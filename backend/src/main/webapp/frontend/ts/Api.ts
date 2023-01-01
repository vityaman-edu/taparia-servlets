import {Picture} from "./picture/Picture.js"
import {Utility} from "./Utility.js";
import {Point} from "./picture/figure/Point.js";
import {TapResult} from "./TapResult.js";

export class Api {
    private sessionCookie: string = ""

    constructor(private readonly host: string) {
    }

    getLatestPictureById(ownerId: string, name: string): Promise<Picture> {
        return new Promise((resolve, reject) =>
            $.ajax({
                type: 'GET',
                url: this.relative("pictures"),
                data: {
                    "owner_id": ownerId,
                    "name": name
                },
                timeout: 1500,
                success: (data: object) => {
                    const json = Utility.deepConvertToMap(data)
                    const picture = Picture.fromJson(json)
                    resolve(picture)
                },
                error: reject
            })
        )
    }

    tap(pictureId: Picture.Id, point: Point): Promise<TapResult> {
        return new Promise((resolve, reject) => {
            $.ajax({
                type: 'POST',
                url: this.relative("picture/taps"),
                contentType: "application/json",
                data: JSON.stringify({
                    "picture": {
                        "owner": pictureId.owner,
                        "name": pictureId.name,
                        "version": pictureId.version,
                    },
                    "point": {
                        "x": point.x,
                        "y": point.y
                    }
                }),
                timeout: 1500,
                success: (data: object) => {
                    const json = Utility.deepConvertToMap(data)
                    const result = TapResult.fromJson(json)
                    resolve(result)
                },
                error: reject
            });
        })
    }

    getTapResults(pictureId: Picture.Id): Promise<Array<TapResult>> {
        return new Promise((resolve, reject) =>
            $.ajax({
                type: 'GET',
                url: this.relative("picture/taps"),
                data: {
                    "owner": pictureId.owner,
                    "name": pictureId.name,
                    "version": pictureId.version
                },
                timeout: 1500,
                success: (data: object) => {
                    resolve(
                        ((data as Array<object>)
                                .map(Utility.deepConvertToMap) as Array<Map<string, any>>
                        ).map(TapResult.fromJson)
                    )
                },
                error: reject
            })
        )
    }

    deleteTapResults(
        pictureId: Picture.Id, center: Point, radius: number
    ): Promise<Array<TapResult>> {
        return new Promise((resolve, reject) =>
            $.ajax({
                type: 'DELETE',
                url: this.relative("picture/taps"),
                contentType: "application/json",
                data: JSON.stringify({
                    picture_id: {
                        owner: pictureId.owner,
                        name: pictureId.name,
                        version: pictureId.version,
                    },
                    point: {
                        x: center.x,
                        y: center.y
                    },
                    radius: radius
                }),
                timeout: 1500,
                success: (data: object) => {
                    resolve(
                        ((data as Array<object>)
                                .map(Utility.deepConvertToMap) as Array<Map<string, any>>
                        ).map(TapResult.fromJson)
                    )
                },
                error: reject
            })
        )
    }

    initUser(): Promise<String> {
        return new Promise((resolve, reject) =>
            $.ajax({
                type: 'GET',
                xhrFields: {
                    withCredentials: true
                },
                url: this.relative("user/init"),
                timeout: 1500,
                success: (data: string, textStatus, request) => {
                    document.cookie += request.getResponseHeader("Set-Cookie")
                    resolve(data)
                },
                error: reject
            })
        )
    }

    private relative(path: string): string {
        return `${this.host}/api/${path}`
    }
}
