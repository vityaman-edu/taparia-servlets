import {Point} from "./figure/Point.js";
import {Drawable} from "./Drawable.js";

export class Segment implements Drawable {
    constructor(
        public readonly start: Point,
        public readonly end: Point,
        public readonly width: number,
        public readonly color: string
    ) {
    }

    draw(ctx: CanvasRenderingContext2D): void {
        ctx.fillStyle = this.color;
        ctx.lineWidth = this.width;
        ctx.beginPath();
        ctx.moveTo(this.start.x, this.start.y);
        ctx.lineTo(this.end.x, this.end.y);
        ctx.stroke();
        ctx.fill();
    }

}