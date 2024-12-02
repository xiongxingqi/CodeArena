<template >

  <div class="container recordContainer" >
    <div class="card">
      <div class="card-header">
        <span class="recordFont">对战记录</span>
        <button type="button" class="btn btn-primary recordButton" @click="pull_page">刷新记录</button>
      </div>
      <div class="card-body">
        <table class="table table-striped table-hover ">
          <thead>
            <tr>
              <th>玩家A</th>
              <th>玩家B</th>
              <th>获胜者</th>
              <th>对战时间</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="record in records" :key="record.record.id">
              <td>
                <img :src="record.avatarA" alt="">
                &nbsp;
                <span>{{record.playerNameA}}</span>
              </td>
              <td >
                <img :src="record.avatarB" alt="">
                &nbsp;
                <span>{{record.playerNameB}}</span>
              </td>
              <td>{{getVector(record.record.loser)}}</td>
              <td>{{record.record.createTime}}</td>
              <td>
                <button type="button" class="btn btn-info" @click = "view_record(record)">查看录像</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>

</template>
<script>
import {ref} from "vue";
import axios from "axios";
import {useStore} from "vuex";
import {useRouter} from "vue-router";

export default {
    name: 'RecordListIndexView',
    components: {
    },
    setup(){
      const records = ref([]);
      const currentPage= ref(1);

      const store = useStore();
      const pull_page = (page) =>{
        axios.get("/record/getRecordList",{
          params:{
            page
          },
          headers:{
            Authorization: "Bearer " + store.state.user.token,
          }
        }).then((resp) =>{
          console.log(resp.data);
          const result = resp.data;
          if(result.code === 1)
            records.value = result.data;
          else alert(result.message);
        }).catch((error)=>{
          console.log(error.status)
        })
      }

      const getVector = (loser) =>{
        if(loser === "A") {
          return "B";
        }else if(loser === "B"){
          return "A"
        }else if(loser === "All"){
          return "Drew";
        }
        return "Non";
      }

      const getGameMapTo2D = (map) =>{
        let g = [];
        for(let i = 0, k=0;i<13;i++){
          let line = [];
          for(let j =0;j<14;j++,k++){
            // line.push(parseInt(map[j]));
            if(map[k] === '0') line.push(0);
            else line.push(1);
          }
          g.push(line);
        }
        return g;
      }

      const router = useRouter();

     const view_record = (record) => {
        store.commit("update_is_record",true);
        store.commit("update_steps",record.record);
        store.commit("updateGameMap",{
          game_map: getGameMapTo2D(record.record.map),
          a_id: record.record.aId,
          a_sx: record.record.aSx,
          a_sy: record.record.aSy,
          b_id: record.record.bId,
          b_sx: record.record.bSx,
          b_sy: record.record.bSy,
        });
        store.commit("update_record_loser",record.record.loser);

        router.push({
          name: "recordContent",
          params:{
            record_id: record.record.id,
          }
        });
     }

      pull_page(currentPage.value);
      return {
        records,
        currentPage,
        pull_page,
        getVector,
        view_record,
      }
    }
}
</script>
<style scoped>
    div.recordContainer{
      margin-top: 20px;
    }
    span.recordFont{
      font-size: 20px;
      font-weight: 600;
    }
    button.recordButton{
      float: right;
    }

    td > img{
      height: 4vh;
      border-radius: 50%;
    }


</style>