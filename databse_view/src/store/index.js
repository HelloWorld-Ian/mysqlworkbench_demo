import {createStore} from 'vuex'
import axios from "axios"

const state={
    table_name:[]
}

const vuex=new createStore({
    state,
    mutations: {
        async getTableName(state){
            await axios.get('/post/controller/getTableName').then(function(res){
                state.table_name=res.data
                alert(state.table_name)
            }).catch(function(error){
                alert(error)
            })
        }
    }
})

export default vuex
