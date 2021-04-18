<template>
    <div class="app-container">
        <cus-wraper>
            <cus-filter-wraper>
                <#if (queryFields ? exists) >
                <#list queryFields as field>
                <el-input v-model="listQuery.${field.property}" placeholder="请输入${field.columnComment}" style="width:200px" clearable></el-input>
                </#list>
                <el-button type="primary" @click="getList" icon="el-icon-search" v-waves>查询</el-button>
                <el-button type="primary" @click="handleCreate" icon="el-icon-plus" v-waves>添加</el-button>
                </#if>
            </cus-filter-wraper>
            <div class="table-container">
                <el-table v-loading="listLoading" :data="list" size="mini" element-loading-text="Loading" fit border highlight-current-row>
                    <#list table.fields as field>
                    <#if (field.propertyType.equals("Date"))>
                    <el-table-column label="${field.columnComment}" align="center">
                        <template slot-scope="scope">
                            <span>{{scope.row.${field.property}|dateTimeFilter}}</span>
                        </template>
                    </el-table-column>
                    <#else>
                    <el-table-column label="${field.columnComment}" prop="${field.property}" align="center"></el-table-column>
                    </#if>
                    </#list>
                    <el-table-column align="center" label="操作">
                        <template slot-scope="scope">
                            <el-button size="mini" type="primary" @click="handleUpdate(scope.row)" icon="el-icon-edit" plain v-waves>编辑</el-button>
                            <cus-del-btn @ok="handleDelete(scope.row)"></cus-del-btn>
                        </template>
                    </el-table-column>
                </el-table>
                <!-- 分页 -->
                <cus-pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList"/>
            </div>

            <el-dialog :title="titleMap[dialogStatus]" :visible.sync="dialogVisible" width="40%" @close="handleDialogClose">
                <el-form ref="dataForm" :model="form" :rules="rules" label-width="100px" class="demo-ruleForm">
                    <#list table.fields as field>
                    <el-form-item label="${field.columnComment}:" prop="${field.propertyName}">
                        <el-input v-model="form.${field.propertyName}"></el-input>
                    </el-form-item>
                   </#list>
                </el-form>
                <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false" v-waves>取 消</el-button>
          <el-button type="primary" @click="submitForm" v-waves>确 定</el-button>
        </span>
            </el-dialog>
        </cus-wraper>
    </div>
</template>

<script>
    import { get${entity}Page, save${entity}, delete${entity} } from "@/api/${moduleName}/${entityPropertyName}";

    export default {
        data() {
            return {
                dialogVisible: false,
                list: [],
                listLoading: true,
                total: 0,
                listQuery: {
                    page: 1,
                    limit: 10,
                  <#if (queryFields) >
                   <#list queryFields as field>
                     ${field.propertyName}:undefined,
                   </#list>
                  </#if>
            },
            input: '',
                    form: {
            <#list table.fields as field>
            ${field.propertyName}: undefined, //${field.comment}
            </#list>
            },
            dialogStatus: "",
                    titleMap: {
                update: "编辑",
                        create: "创建"
            },
            rules: {
                name: [
                    { required: true, message: '请输入项目名称', trigger: 'blur' }
                ]
            }
        }
        },
        created() {
            this.getList();
        },
        methods: {
            getList() {
                this.listLoading = true;
                get${entity}Page(this.listQuery).then(response => {
                    this.list = response.data.list;
                this.total = response.data.total;
                this.listLoading = false;
            });
            },
            handleCreate() {
                this.resetForm();
                this.dialogStatus = "create";
                this.dialogVisible = true;
            },
            handleUpdate(row) {
                this.form = Object.assign({}, row);
                this.dialogStatus = "update";
                this.dialogVisible = true;
            },
            handleDelete(row) {
            <#list table.fields as field>
            <#if  (field.keyFlag) >
                    let id = row.${field.propertyName};
            </#if>
            </#list>
                delete${entity}(id).then(response => {
                    if (response.code == 200) {
                    this.getList();
                    this.submitOk(response.message);
                } else {
                    this.submitFail(response.message);
                }
            });
            },
            submitForm() {
                this.#[[$refs]]#.['dataForm'].validate(valid => {
                    if (valid) {
                        save${entity}(this.form).then(response => {
                            if (response.code == 200) {
                            this.getList();
                            this.submitOk(response.message);
                            this.dialogVisible = false;
                        } else {
                            this.submitFail(response.message);
                        }
                    }).catch(err => { console.log(err);  });
                    }
                });
            },
            resetForm() {
                this.form = {
            <#list table.fields as field>
                ${field.propertyName}: undefined, //${field.comment}
            </#list>
            };
            },
            // 监听dialog关闭时的处理事件
            handleDialogClose(){
                if(this.$refs['dataForm']){
                    this.$refs['dataForm'].clearValidate(); // 清除整个表单的校验
                }
            }
        }
    }
</script>