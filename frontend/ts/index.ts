import {Canvas} from './Canvas.js';
import {Point} from "./picture/figure/Point.js";
import {Ellipse} from "./picture/figure/Ellipse.js";
import {CoordinatesDrawable} from "./CoordinatesDrawable.js";
import {Api} from "./Api.js";
import {Picture} from "./picture/Picture.js";
import {Table} from "./Table.js";
import {TapResult} from "./TapResult.js";

enum UserState {
    TAP_ADD,
    TAP_REMOVE
}

const api = new Api('http://localhost:8080/backend-1.0')
const DOT_R = 4
const COORDINATES = new CoordinatesDrawable()
let CurrentPicture: Picture = null

const canvas = new Canvas(
    document.getElementById('canvas') as HTMLCanvasElement,
    new Point(250, 250)
)

const table = new Table(
    document.getElementById("result-table") as HTMLTableElement
)

document.getElementById("tab-button-tap")
    .onclick = e => setActiveTab(e, "tap")

document.getElementById("tab-button-edit")
    .onclick = e => setActiveTab(e, "edit")

document.getElementById("button-tap-add")
    .onclick = e => setUserState(e, UserState.TAP_ADD)

document.getElementById("button-tap-remove")
    .onclick = e => setUserState(e, UserState.TAP_REMOVE)

document.getElementById("button-require-figure-100")
    .onclick = e => requirePicture(CurrentPicture.id.owner, 100)

document.getElementById("button-require-figure-200")
    .onclick = e => requirePicture(CurrentPicture.id.owner, 200)

document.getElementById("button-require-figure-300")
    .onclick = e => requirePicture(CurrentPicture.id.owner, 300)

document.getElementById("button-tap-add").click()

function createPoint(center: Point, color: string) {
    return new Ellipse(
        color,
        new Point(center.x, center.y),
        new Point(DOT_R, DOT_R)
    )
}

function drawableTap(tap: TapResult) {
    return createPoint(
        tap.point,
        ((tap.isHit) ? ("#0F0") : ("#F00"))
    )
}

setup();

function setup() {
    api.initUser().then(username => requirePicture(username as string, 100))
}

function requirePicture(username: string, r: number) {
    api.getLatestPictureById(username, `r${r}`)
        .then(updatePicture)
}

function updatePicture(picture: Picture) {
    CurrentPicture = picture
    canvas.clear()
    table.clear()
    canvas.draw(CurrentPicture.figure)
    canvas.draw(COORDINATES)
    api.getTapResults(CurrentPicture.id).then(results =>
        results.forEach(result => {
            table.append(result)
            canvas.draw(drawableTap(result))
        })
    )
    canvas.draw(COORDINATES)
}

export function setActiveTab(evt: MouseEvent, mode: string) {
    Array.from(document.getElementsByClassName("tabcontent"))
        .map(e => e as HTMLElement)
        .forEach(element => element.style.display = "none")

    Array.from(document.getElementsByClassName("tablinks"))
        .map(e => e as HTMLElement)
        .forEach(e =>
            e.className = e.className.replace(" active", "")
        )

    document.getElementById(mode).style.display = "block";
    (evt.currentTarget as HTMLElement).className += " active";
}

export function setUserState(evt: MouseEvent, state: UserState) {
    Array.from(document.getElementsByClassName("state-switch-button"))
        .map(e => e as HTMLElement)
        .forEach(e =>
            e.className = e.className.replace(" active", "")
        );

    (evt.currentTarget as HTMLElement).className += " active"

    if (state == UserState.TAP_ADD) {
        canvas.setMouseClickListener(pos => {
            api.tap(CurrentPicture.id, pos).then(result => {
                canvas.draw(
                    createPoint(
                        result.point,
                        ((result.isHit) ? ("#0F0") : ("#F00"))
                    )
                )
                console.assert(
                    result.pictureVersion == CurrentPicture.id.version,
                    `${result.pictureVersion} != ${CurrentPicture.id.version}`
                )
                table.append(result)
            })
        })
    } else if (state == UserState.TAP_REMOVE) {
        canvas.setMouseClickListener(pos => {
            api.deleteTapResults(CurrentPicture.id, pos, 10).then(deleted => {
                table.remove(deleted)
                canvas.clear()
                canvas.draw(CurrentPicture.figure)
                canvas.draw(COORDINATES)
                table.results.forEach(r => canvas.draw(drawableTap(r)))
            })
        })
    } else {
        console.error(`State ${state} not handled`)
    }
}
