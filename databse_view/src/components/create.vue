<template>
    <el-card style="float:left">
    <el-form label-width="auto" >
    <div style="height:60px;margin-bottom:20px">
    <h2 style="margin-left:20px;color: dodgerblue;margin-top:5px;float:left">create new table</h2>
    <img src="../assets/add.svg" style="width:20px;height:20px;margin-top:13px;margin-left:310px" @click="add" />
    <img src="../assets/decrease.svg" style="width:20px;height:20px;margin-top:13px;margin-left:310px" @click="decrease" />
    </div>
    <el-form-item  label="表名">
    <el-input v-model="table_name" style="width:70%"></el-input>
    </el-form-item>
    <div  style="margin-top:50px">
    <el-form-item  :label="'属性名'+0">
    <el-input v-model="field[0].name_" style="width:70%" readonly></el-input>
    </el-form-item>
     <el-form-item  :label="'类型'+0">
    <el-input v-model="field[0].type" style="width:70%" readonly></el-input>
    </el-form-item>
    <el-checkbox-group v-model="checkList[0]" style="margin-left:60px">
    <el-checkbox label="not null" ></el-checkbox>
    <el-checkbox label="auto_increment"></el-checkbox>
    <el-checkbox label="unique"></el-checkbox>
    <el-checkbox label="primary key"></el-checkbox>
    </el-checkbox-group>
    </div>
    <div v-for="count in counts" :key="count" style="margin-top:50px">
    <el-form-item  :label="'属性名'+count">
    <el-input v-model="field[count].name_" style="width:70%"></el-input>
    </el-form-item>
     <el-form-item  :label="'类型'+count">
    <el-input v-model="field[count].type" style="width:70%"></el-input>
    </el-form-item>
    <el-checkbox-group v-model="checkList[count]" style="margin-left:60px">
    <el-checkbox label="not null"></el-checkbox>
    <el-checkbox label="unique"></el-checkbox>
    </el-checkbox-group>
    </div>
     <el-button type="primary" @click="submit" style="margin-left:40px;margin-top:20px">确认创建</el-button>
    </el-form>
    </el-card>
    <el-card>
    <el-input
    type="textarea"
    :rows="15"
    v-model="sql"
    style="font-size:20px">
    </el-input>
    </el-card>
</template>

<script>
export default {
    data(){
        return{
            table_name:'default',
            counts:0,
            field:[{
                name_:"id",
                type:"int(255)"
            }],
            checkList:[["not null","auto_increment","unique","primary key"]],
            sql:'create table '+'`'+'default'+'`'+'\n'+'('+'\n'+'   id int(255) not null auto_increment unique primary key'+'\n'+');',
            body:''
        }
    },
    methods:{
        add(){
            let l=this.field.length
            this.field.push({
                name_:'column_'+l,
                type:'int(255)'
            })
            this.checkList.push([])
            this.counts++
        },
        decrease(){
            if(this.field.length==1){
                return
            }
            this.counts--
            this.field.pop()
            this.checkList.pop()
        },
        refresh(filed,checkList){
            let body=''
            let l=filed.length
            for(let i=0;i<l;i++){
                let str='\n   '+filed[i].name_+' '+filed[i].type+' '
                let ll=checkList[i].length
                for(let j=0;j<ll;j++){
                    str+=(checkList[i][j]+' ')
                }
                if(i==l-1){
                     body+=str
                }else{
                     body+=(str+',')
                }
            }
            return body
        },
        async submit(){
            let columns=[]
            this.field.forEach(function(item){
                     columns.push(item.name_)
            })
            let param={
                sql:this.sql,
                name:this.table_name,
                columns:JSON.stringify(columns)
            }
            await this.axios.post('/post/controller/getSQL',this.$qs.stringify(param)).then(function(res){
                alert(res.data)
            }).catch(function(error){
                alert(error)
            })
            this.$router.go(0)
        }
    },
    watch:{
        'table_name':function(val){
            this.sql='create table '+'`'+val+'`'+'\n'+'('+ this.body+'\n'+');'
        },
        'body':function(val){
            this.sql='create table '+'`'+this.table_name+'`'+'\n'+'('+ val+'\n'+');'
        },
        'field':{
            handler:function(val){
                this.body=this.refresh(val,this.checkList)
            },
            deep:true
        },
        'checkList':{
             handler:function(val){
                this.body=this.refresh(this.field,val)
            },
            deep:true
        }
    }
}
</script>

<style scoped>
.el-card{
    margin-left:50px;
    width: 600px;
    margin-right:80px
}

img:hover{
    cursor: pointer;
}

.el-checkbox{
    margin-right:10px
}

</style>