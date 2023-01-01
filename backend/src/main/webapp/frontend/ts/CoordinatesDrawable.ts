import {Point} from "./picture/figure/Point.js";
import {Segment} from "./picture/Segment.js";
import {Ellipse} from "./picture/figure/Ellipse.js";
import {Drawable} from "./picture/Drawable.js";

const INF = 999

export class CoordinatesDrawable implements Drawable {
    private origin = Drawable.union([
        new Segment(
            new Point(-INF, 0),
            new Point(INF, 0),
            2,
            '#A00'
        ),
        new Segment(
            new Point(0, -INF),
            new Point(0, INF),
            2,
            '#A00'
        ),
        new Ellipse(
            '#00F',
            new Point(0, 0),
            new Point(3, 3)
        )
    ])

    draw(ctx: CanvasRenderingContext2D): void {
        this.origin.draw(ctx);
    }
}