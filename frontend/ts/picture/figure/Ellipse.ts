import {Point} from "./Point.js";
import {TrivialFigure} from "./TrivialFigure.js";

export class Ellipse extends TrivialFigure {
    constructor(
        color: string,
        public readonly center: Point,
        public readonly radius: Point
    ) {
        super("ellipse", color)
    }

    draw(ctx: CanvasRenderingContext2D): void {
        ctx.fillStyle = this.color;
        ctx.beginPath();
        ctx.ellipse(
            this.center.x, this.center.y,
            this.radius.x, this.radius.y,
            0, 0, 2 * Math.PI
        );
        ctx.fill();
    }
}

export namespace Ellipse {
    export function fromJson(json: Map<string, any>): Ellipse {
        return new Ellipse(
            json.get("color") as string,
            Point.fromJson(json.get('center') as Map<string, any>),
            Point.fromJson(json.get('radius') as Map<string, any>)
        )
    }
}