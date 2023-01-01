import {Point} from "./picture/figure/Point.js";

export class TapResult {
    constructor(
        public readonly pictureVersion: string,
        public readonly point: Point,
        public readonly isHit: boolean
    ) {
    }

    equals(other: TapResult): boolean {
        return this.pictureVersion === other.pictureVersion
            && this.isHit === other.isHit
            && this.point.equals(other.point)
    }
}

export namespace TapResult {
    export function fromJson(json: Map<string, any>): TapResult {
        return new TapResult(
            json.get("pictureVersion"),
            Point.fromJson(json.get("point")),
            json.get("is_hit")
        )
    }
}