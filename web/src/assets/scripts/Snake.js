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
        this.direction = -1 // -1表示没指令 0,1,2,3分别表示上右下左
        this.status = "idle";

        this.dr = [-1, 0, 1, 0];
        this.dc = [0, 1, 0, -1];

        this.round = 0;//回合数
        this.eps = 1e-2;//允许误差

        this.eye_direction = 0;
        if (this.id === 1) this.eye_direction = 2;

        this.eye_dx = [
            [-1, 1],
            [1, 1],
            [1, -1],
            [-1, -1]
        ]
        this.eye_dy = [
            [-1, -1],
            [-1, 1],
            [1, 1],
            [1, -1]
        ]

    }

    start() {

    }
    check_tail_increasing() {
        if (this.round <= 10) return true;
        else {
            if (this.round % 3 == 1) return true;
        }

        return false;
    }
    next_step() {

        this.status = "move";
        this.round++;
        this.next_cell = new Cell(this.cells[0].r + this.dr[this.direction], this.cells[0].c + this.dc[this.direction]);
        let k = this.cells.length;
        this.eye_direction = this.direction;
        this.direction = -1;
        // console.log(this.next_cell.r, this.next_cell.c);

        // console.log(k);
        if (!this.game_map.check_valid(this.next_cell)) {
            this.status = "die";
            return;
        }
        for (let i = k; i > 0; i--) this.cells[i] = JSON.parse(JSON.stringify(this.cells[i - 1]));

    }
    update_move() {
        let dx = this.next_cell.x - this.cells[0].x;
        let dy = this.next_cell.y - this.cells[0].y;
        let distance = Math.sqrt(dx * dx + dy * dy);

        if (distance < this.eps) {
            this.cells[0] = this.next_cell;
            this.status = "idle";

            if (!this.check_tail_increasing()) this.cells.pop();
            this.next_cell = null;
        } else {
            let move_size = this.speed * this.time_delta / 1000;
            this.cells[0].x = this.cells[0].x + move_size * dx / distance;
            this.cells[0].y = this.cells[0].y + move_size * dy / distance;

            // let move_size = this.speed * this.time_delta / 1000;
            // this.cells[0].x = this.cells[0].x + this.dc[this.direction] * move_size;
            // this.cells[0].y = this.cells[0].y + this.dr[this.direction] * move_size;
            if (!this.check_tail_increasing()) {
                const s = this.cells.length;
                let dx = this.cells[s - 2].x - this.cells[s - 1].x;
                let dy = this.cells[s - 2].y - this.cells[s - 1].y;
                let distance = Math.sqrt(dx * dx + dy * dy);

                this.cells[s - 1].x = this.cells[s - 1].x + move_size * dx / distance;
                this.cells[s - 1].y += move_size * dy / distance;
            }
        }


    }
    update() {
        if (this.status === "move") this.update_move();
        this.reader();

    }

    set_direction(d) {
        this.direction = d;
    }

    reader() {
        const L = this.game_map.L;
        const ctx = this.game_map.ctx;
        ctx.fillStyle = this.color;
        if (this.status === "die") ctx.fillStyle = "white";
        for (let cell of this.cells) {
            ctx.beginPath();
            ctx.arc(cell.x * L, cell.y * L, L * 0.4, 0, Math.PI * 2);
            ctx.fill();
        }

        for (let i = 1; i < this.cells.length; i++) {
            const a = this.cells[i], b = this.cells[i - 1];
            if (Math.abs(a.x - b.x) < this.eps && Math.abs(a.y - b.y) < this.eps) continue;
            else if (Math.abs(a.x - b.x) < this.eps) {
                let min_y = Math.min(a.y, b.y);
                let min_x = a.x - 0.4;
                ctx.fillRect(min_x * L, min_y * L, L * 0.8, Math.abs(a.y - b.y) * L);
            } else if (Math.abs(a.y - b.y) < this.eps) {
                let min_x = Math.min(a.x, b.x);
                let min_y = a.y - 0.4;
                ctx.fillRect(min_x * L, min_y * L, Math.abs(a.x - b.x) * L, L * 0.8);
            }
        }

        ctx.fillStyle = "black";

        for (let i = 0; i < 2; i++) {
            ctx.beginPath();
            ctx.arc(
                (this.cells[0].x + this.eye_dx[this.eye_direction][i] * 0.15) * L,
                (this.cells[0].y + this.eye_dy[this.eye_direction][i] * 0.15) * L, 0.05 * L, 0, 2 * Math.PI);
            ctx.fill();
        }

    }

}
