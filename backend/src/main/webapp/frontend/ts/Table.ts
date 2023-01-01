import {TapResult} from "./TapResult.js";

export class Table {
    private readonly table: HTMLTableElement
    public results: Array<TapResult>

    constructor(table: HTMLTableElement) {
        this.table = table
        this.results = []
    }

    append(result: TapResult) {
        this.results.push(result);
        (new Table.Entry(result)).appendTo(this.table)
    }

    clear() {
        this.remove(this.results)
    }

    remove(results: Array<TapResult>) {
        this.results = this.results.filter(r => !results.some(e => e.equals(r)))
        this.redraw()
    }

    redraw() {
        console.log(this.table)
        this.table.innerHTML =
            "<tr>\n" +
            "  <th>Time</th>\n" +
            "  <th>Point</th>\n" +
            "  <th>Result</th>\n" +
            "  <th>Latency</th>\n" +
            "</tr>"
        this.results.forEach(r =>
            (new Table.Entry(r)).appendTo(this.table)
        )
    }
}

export namespace Table {
    export class Entry {
        constructor(
            readonly tapResult: TapResult
        ) {
        }

        /**
         * <tr>
         *   <td>17:34</td>
         *   <td>(10, 21)</td>
         *   <td>HIT</td>
         *   <td>0.001</td>
         * </tr>
         */
        appendTo(table: HTMLTableElement) {
            const row = table.insertRow(1)
            row.insertCell(0).innerHTML = "TODO"
            row.insertCell(1).innerHTML = this.tapResult.isHit ? "HIT" : "MISS"
            row.insertCell(2).innerHTML = this.tapResult.point.toString()
            row.insertCell(3).innerHTML = "TODO"
        }

        // private currentTimeAsString() {
        //     const date = new Date(this.currentTime * 1000)
        //     const hours = date.getHours()
        //     const minutes = "0" + date.getMinutes()
        //     const seconds = "0" + date.getSeconds()
        //     return hours + ':' + minutes.slice(-2) + ':' + seconds.slice(-2);
        // }
    }

    export namespace Entry {
        export class Status {
            constructor(private text: string) {
            }

            toString() {
                return this.text
            }
        }

        export namespace Status {
            export function hit() {
                return new Status("HIT")
            }

            export function miss() {
                return new Status("MISS")
            }

            export function error(text: string) {
                return new Status(text)
            }
        }

    }
}