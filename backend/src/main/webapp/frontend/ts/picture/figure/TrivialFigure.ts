import {Figure} from "./Figure.js";

export abstract class TrivialFigure extends Figure {
    protected constructor(
        type: string,
        public readonly color: string
    ) {
        super(type)
    }
}

