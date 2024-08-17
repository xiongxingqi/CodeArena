import { AcGameObject } from "./AcGameObject";
export class GameMap extends AcGameObject {
    constructor(ctx, parent) {
        super();

        this.ctx = ctx;
        this.parent = parent;
        this.L = 0;

        this.rows = 13;
        this.cols = 13;

        this.inner_walls_count = 20;
        this.g = [];
    }

    start() {

    }
    update_size() {
        this.L = parseInt(Math.min(this.parent.clientWidth / this.cols, this.parent.clientHeight / this.rows));//转换为int类型防止因浮点造成渲染的方块之间有间隙
        this.ctx.canvas.width = this.L * this.cols;
        this.ctx.canvas.height = this.L * this.rows;
    }
    update() {
        this.update_size();
        this.reader();
    }

    reader() {
        const color_even = "#a2d149"; const color_odd = "#aad751";
        for (let r = 0; r < this.rows; r++) {
            for (let c = 0; c < this.cols; c++) {
                if ((r + c) % 2 === 0) {
                    this.ctx.fillStyle = color_odd;
                } else {
                    this.ctx.fillStyle = color_even;
                }
                this.ctx.fillRect(c * this.L, r * this.L, this.L, this.L);
            }
        }
    }

}