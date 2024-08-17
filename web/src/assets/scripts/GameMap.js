import { Wall } from "./Wall";
import { AcGameObject } from "./AcGameObject";
export class GameMap extends AcGameObject {
    constructor(ctx, parent) {
        super();

        this.ctx = ctx;
        this.parent = parent;
        this.L = 0;

        this.rows = 13;
        this.cols = 13;

        this.inner_walls_count = 30;
        this.walls = [];
    }

    create_walls() {
        const g = [];//创建地图某个位置是否生成墙的二维数组
        for (let r = 0; r < this.rows; r++) {
            g[r] = [];
            for (let c = 0; c < this.cols; c++) {
                g[r][c] = false;
            }
        }
        //填充左右外边界
        for (let r = 0; r < this.rows; r++) g[r][0] = g[r][this.cols - 1] = true;

        for (let c = 0; c < this.cols; c++) g[0][c] = g[this.rows - 1][c] = true;

        //随机生成内部墙
        for (let i = 0; i < this.inner_walls_count / 2; i++) {
            for (let j = 0; j < 1000; j++) {
                let row = parseInt(Math.random() * this.rows);
                let col = parseInt(Math.random() * this.cols);
                if (g[row][col] || g[col][row]) continue;
                g[row][col] = g[col][row] = true;
                break;
            }
        }

        for (let r = 0; r < this.rows; r++) {
            for (let c = 0; c < this.cols; c++) {
                if (g[r][c]) {
                    this.walls.push(new Wall(r, c, this));
                }
            }
        }
    }

    start() {
        this.create_walls();
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