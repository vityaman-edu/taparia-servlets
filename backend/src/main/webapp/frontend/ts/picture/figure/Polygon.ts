import {Point} from "./Point.js";
import {TrivialFigure} from "./TrivialFigure.js";

export class Polygon extends TrivialFigure {
    constructor(
        color: string,
        public readonly points: Array<Point>
    ) {
        super("polygon", color);
    }

    draw(ctx: CanvasRenderingContext2D): void {
        const points = this.points
        ctx.beginPath()
        ctx.moveTo(points[0].x, points[0].y)
        for (let i = 1; i < points.length; i++) {
            ctx.lineTo(points[i].x, points[i].y)
        }
        ctx.closePath()
        ctx.fillStyle = this.color
        ctx.fill()
    }
}

export namespace Polygon {
    export function fromJson(json: Map<string, any>): Polygon {
        return new Polygon(
            json.get("color") as string,
            (json.get('points') as Array<Map<string, any>>)
                .map(Point.fromJson)
        )
    }
}