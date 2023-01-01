import {FigureFactory} from "./figure/FigureFactory.js";
import {Figure} from "./figure/Figure.js";

export class Picture {
    constructor(
        public readonly id: Picture.Id,
        public readonly figure: Figure
    ) {
    }
}

export namespace Picture {
    export class Id {
        constructor(
            public readonly owner: string,
            public readonly name: string,
            public readonly version: string
        ) {
        }
    }

    export function fromJson(json: Map<string, any>): Picture {
        return new Picture(
            new Id(
                json.get('owner'),
                json.get('name'),
                json.get('version'),
            ),
            FigureFactory.fromJson(json.get('figure'))
        )
    }
}