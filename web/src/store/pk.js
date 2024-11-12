
export default {
    state: {
        status: "matching", // matching 匹配界面 playing 对战界面
        socket: null,
        opponent_username: "",
        opponent_photo: "",
        game_map: null,

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
        updateGameMap(state,gameMap){
            state.game_map =  gameMap;
        }
    },
    actions: {

    },
    modules: {
    }
}