import { AcGameObject } from "./AcGameObject";
import { Cell } from "./Cell";
export class Snake extends AcGameObject {
    constructor(info, game_map) {
        super();

        this.id = info.id;
        this.color = info.color;
        this.game_map = game_map;

        this.cells = [new Cell(info.r, info.c)];
        this.next_cell = null;

        this.speed = 5;
        this.direction = info.direction // -1表示没指令 0,1,2,3分别表示上右下左
        this.status = "idle";

        this.dr = [-1, 0, 1, 0];
        this.dc = [0, 1, 0, -1];

        this.round = 0;//回合数
        this.eps = 1e-2;//允许误差

    }

    start() {

    }

    update_move() {
        let move_size = this.speed * this.time_delta / 1000;
        this.cells[0].x = this.cells[0].x + this.dc[this.direction] * move_size;
        this.cells[0].y = this.cells[0].y + this.dr[this.direction] * move_size;

    }
    update() {
        this.update_move();
        this.reader();

    }

    reader() {
        const L = this.game_map.L;
        const ctx = this.game_map.ctx;
        ctx.fillStyle = this.color;
        for (let cell of this.cells) {
            ctx.beginPath();
            ctx.arc(cell.x * L, cell.y * L, L * 0.5, 0, Math.PI * 2);
            ctx.fill();
        }
    }

}
