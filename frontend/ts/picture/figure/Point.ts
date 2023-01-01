export class Point {
    constructor(
        public readonly x: number,
        public readonly y: number,
    ) {
    }

    equals(other: Point): boolean {
        return this.x === other.x
            && this.y === other.y
    }

    toString(): string {
        return `(${this.x}, ${this.y})`
    }
}

export namespace Point {
    export function fromJson(json: Map<string, any>) {
        return new Point(
            json.get('x') as number,
            json.get('y') as number
        );
    }
}
