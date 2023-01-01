import {Figure} from "./Figure.js";

export class Negative extends Figure {
    constructor(
        public readonly child: Figure
    ) {
        super("negative")
    }

    draw(ctx: CanvasRenderingContext2D): void {
        this.child.draw(ctx)
    }
}