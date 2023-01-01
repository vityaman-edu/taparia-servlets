import {Figure} from "./Figure.js";
import {FigureAggregator} from "./FigureAggregator.js";

export class Intersection extends FigureAggregator {
    constructor(
        children: Array<Figure>
    ) {
        super("intersection", children)
    }

}