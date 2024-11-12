import { Wall } from "./Wall";
import { AcGameObject } from "./AcGameObject";
import { Snake } from "./Snake";
export class GameMap extends AcGameObject {
    constructor(ctx, parent,store) {
        super();

        this.store = store;
        this.ctx = ctx;
        this.parent = parent;

        this.L = 0;

        this.rows = 13;
        this.cols = 14;

        this.inner_walls_count = 20;
        this.walls = [];

        this.snakes = [
            new Snake({ id: 0, color: '#f94848', r: this.rows - 2, c: 1 }, this),
            new Snake({ id: 1, color: '#4876ec', r: 1, c: this.cols - 2 }, this),
        ];
    }

    check_valid(cell) {
        for (let i = 0; i < this.walls.length; i++) {
            if (cell.r === this.walls[i].r && cell.c === this.walls[i].c) return false;
        }

        for (let snake of this.snakes) {
            let k = snake.cells.length;
            if (!snake.check_tail_increasing) k--;
            for (let i = 0; i < k; i++) {
                if (cell.r === snake.cells[i].r && cell.c === snake.cells[i].c) return false;
            }
        }
        return true;
    }


    create_walls() {
        const g = this.store.state.pk.game_map;
        
        for (let r = 0; r < this.rows; r++) {
            for (let c = 0; c < this.cols; c++) {
                if (g[r][c]) {
                    this.walls.push(new Wall(r, c, this));
                }
            }
        }

        return true;
    }
    check_ready() {
        for (let snake of this.snakes) {
            if (snake.direction === -1 || snake.status !== "idle") return false;
        }
        return true;
    }
    next_step() {
        for (let snake of this.snakes) snake.next_step();
    }
    add_controller_event() {
        this.ctx.canvas.focus();
        const [snake1, snake2] = this.snakes;
        this.ctx.canvas.addEventListener("keydown", e => {
            let key = e.key;
            if (key === "w") {
                snake1.set_direction(0);
            } else if (key === "d") {
                snake1.set_direction(1);
            } else if (key === "s") {
                snake1.set_direction(2);
            } else if (key === "a") {
                snake1.set_direction(3);
            } else if (key === "ArrowUp") {
                snake2.set_direction(0);
            } else if (key === "ArrowRight") {
                snake2.set_direction(1);
            } else if (key === "ArrowDown") {
                snake2.set_direction(2);
            } else if (key === "ArrowLeft") {
                snake2.set_direction(3);
            }
        });
    }

    start() {
        this.create_walls();
        this.add_controller_event();
    }
    update_size() {
        this.L = parseInt(Math.min(this.parent.clientWidth / this.cols, this.parent.clientHeight / this.rows));//转换为int类型防止因浮点造成渲染的方块之间有间隙
        this.ctx.canvas.width = this.L * this.cols;
        this.ctx.canvas.height = this.L * this.rows;
    }
    update() {
        this.update_size();
        if (this.check_ready()) this.next_step();
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