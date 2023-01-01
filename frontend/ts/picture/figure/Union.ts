import {Figure} from "./Figure.js";
import {FigureAggregator} from "./FigureAggregator.js";

export class Union extends FigureAggregator {
    constructor(
        children: Array<Figure>
    ) {
        super("union", children)
    }
}
