const AC_GAME_OBJECTS = [];

export class AcGameObject {
    constructor() {
        AC_GAME_OBJECTS.push(this);
        this.time_delta = 0;
        this.has_called_start = false;
    }
    start() {//开只执行一次

    }
    update() {//更新时执行一次

    }
    on_destroy() {//销毁前执行

    }

    destroy() {//销毁
        this.on_destroy();

        for (let index in AC_GAME_OBJECTS) {
            const obj = AC_GAME_OBJECTS[index]
            if (obj === this) {
                AC_GAME_OBJECTS.splice(index);
                break;
            }
        }
    }
}

let last_timestamp;//上一次的时间戳

const step = timestamp => {
    // console.log(timestamp - last_timestamp);
    for (let index in AC_GAME_OBJECTS) {
        const obj = AC_GAME_OBJECTS[index];
        if (obj.has_called_start === false) {
            obj.has_called_start = true;
            obj.start();
        } else {
            obj.time_delta = timestamp - last_timestamp;
            obj.update();
        }
    }

    last_timestamp = timestamp;
    requestAnimationFrame(step);
}

requestAnimationFrame(step);
