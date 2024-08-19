import { Wall } from "./Wall";
import { AcGameObject } from "./AcGameObject";
import { Snake } from "./Snake";
export class GameMap extends AcGameObject {
    constructor(ctx, parent) {
        super();

        this.ctx = ctx;
        this.parent = parent;
        this.L = 0;

        this.rows = 13;
        this.cols = 14;

        this.inner_walls_count = 20;
        this.walls = [];

        this.snakes = [
            new Snake({ id: 0, color: '#f94848', r: this.rows - 2, c: 1, direction: 1 }, this),
            new Snake({ id: 1, color: '#4876ec', r: 1, c: this.cols - 2, direction: 3 }, this),
        ];
    }

    check_connectivity(g, sx, sy, ex, ey) {
        if (sx === ex && sy === ey) return true;
        g[sx][sy] = true;

        let dx = [0, 1, 0, -1], dy = [1, 0, -1, 0];
        for (let i = 0; i < 4; i++) {
            let x = sx + dx[i], y = sy + dy[i];
            if (!g[x][y] && this.check_connectivity(g, x, y, ex, ey)) return true;
        }
        return false;
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
                if (g[row][col] || g[this.rows - row - 1][this.cols - col - 1]) continue;
                if (row === this.rows - 2 && col === 1 || row === 1 && col === this.cols - 2) continue;
                g[row][col] = g[this.rows - row - 1][this.cols - col - 1] = true;
                break;
            }
        }
        //检查左下角是否与右上角是否开连通
        const g_copy = JSON.parse(JSON.stringify(g));
        if (!this.check_connectivity(g_copy, this.rows - 2, 1, 1, this.cols - 2)) return false;

        for (let r = 0; r < this.rows; r++) {
            for (let c = 0; c < this.cols; c++) {
                if (g[r][c]) {
                    this.walls.push(new Wall(r, c, this));
                }
            }
        }

        return true;
    }

    start() {
        for (let i = 0; i < 1000; i++)
            if (this.create_walls()) break;
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