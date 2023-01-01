import {Ellipse} from "./Ellipse.js";
import {Polygon} from "./Polygon.js";
import {Figure} from "./Figure.js";
import {Intersection} from "./Intersection.js";
import {Union} from "./Union.js";
import {Negative} from "./Negative.js";

export namespace FigureFactory {
    export function fromJson(json: Map<string, any>): Figure {
        switch (json.get('type') as string) {
            case 'ellipse':
                return Ellipse.fromJson(json)
            case 'polygon':
                return Polygon.fromJson(json)
            case 'intersection':
                return intersectionFromJson(json)
            case 'union':
                return unionFromJson(json)
            case 'negative':
                return negativeFromJson(json)
            default:
                throw new Error('type ' + json.get('type') + ' is not supported')
        }
    }

    function intersectionFromJson(json: Map<string, any>) {
        return new Intersection(
            (json.get('children') as Array<Map<string, any>>)
                .map(fromJson)
        )
    }

    function unionFromJson(json: Map<string, any>) {
        return new Union(
            (json.get('children') as Array<Map<string, any>>)
                .map(fromJson)
        )
    }

    function negativeFromJson(json: Map<string, any>) {
        return new Negative(fromJson(json.get("child") as Map<string, any>))
    }
}
