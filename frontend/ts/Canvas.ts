import {Point} from "./picture/figure/Point.js";
import {Drawable} from "./picture/Drawable.js";
import {Polygon} from "./picture/figure/Polygon.js";

type MouseEventCallback = (mousePosition: Point) => void

export class Canvas {
    constructor(
        private readonly canvas: HTMLCanvasElement,
        private readonly origin: Point
    ) {
    }

    draw(drawable: Drawable): Canvas {
        const ctx = this.canvas.getContext("2d")
        ctx.save();
        ctx.translate(this.origin.x, this.origin.y)
        ctx.scale(1, -1)
        drawable.draw(ctx)
        ctx.restore()
        return this;
    }

    clear() {
        const INF = 9999
        this.draw(new Polygon(
            "#FFFFFF", [
                new Point(-INF, -INF),
                new Point(INF, -INF),
                new Point(INF, INF),
                new Point(-INF, INF)
            ]
        ))
    }

    setMouseClickListener(callback: MouseEventCallback): Canvas {
        this.canvas.onclick = ((e: MouseEvent) => {
            const mousePosition = new Point(e.clientX, e.clientY)
            callback(this.translate(mousePosition))
        }).bind(this)
        return this;
    }

    private translate(point: Point) {
        const topLeftCorner = this.topLeftCorner()
        return new Point(
            point.x - topLeftCorner.x - this.origin.x,
            -(point.y - topLeftCorner.y - this.origin.y)
        )
    }

    private topLeftCorner(): Point {
        const rectangle = this.canvas.getBoundingClientRect();
        return new Point(rectangle.left, rectangle.top)
    }
}
