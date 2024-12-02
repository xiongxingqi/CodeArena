export default {
    state: {
        is_record: false,
        a_steps: "",
        b_steps: "",
        record_loser: "All",
    },
    getters: {
    },
    mutations: {
        update_is_record(state,is_record){
            state.is_record = is_record;
        },
        update_steps(state,data){
            console.log(data);
            state.a_steps = data.asteps;
            state.b_steps = data.bsteps;
        },
        update_record_loser(state,loser){
            state.record_loser = loser;
        }
    },
    actions: {
    },
    modules: {
    }
}