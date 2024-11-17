
export default {
    state: {
        status: "matching", // matching 匹配界面 playing 对战界面
        socket: null,
        opponent_username: "",
        opponent_photo: "",
        a_id: 0,
        a_sx: 0,
        a_sy: 0,
        b_id: 0,
        b_sx: 0,
        b_sy: 0,
        game_map: null,
        game_object: null,
        lower: "none" // none All A B
    },
    getters: {
    },
    mutations: {
        updateOpponent(state,opponent){
            state.opponent_photo = opponent.photo;
            state.opponent_username = opponent.username;
        },
        updateSocket(state,socket){
            state.socket = socket;
        },
        updateStatus(state,status){
            state.status = status;
        },
        updateGameMap(state,game){
            state.game_map =  game.game_map;
            state.a_id = game.a_id;
            state.a_sx = game.a_sx;
            state.a_sy = game.a_sy;
            state.b_id = game.b_id;
            state.b_sx = game.b_sx;
            state.b_sy = game.b_sy;
        },
        updateGameObject(state,game_object){
            state.game_object = game_object;
        },
        updateLoser(state,loser){
            state.loser=loser;
        }
    },
    actions: {

    },
    modules: {
    }
}