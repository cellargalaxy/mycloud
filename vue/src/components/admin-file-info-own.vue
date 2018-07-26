<template>
  <el-container>
    <el-header>

    </el-header>
    <el-main>
      <el-row>
        <el-col :span="24/columnCount" v-for="(fileInfoOwn, fileInfoOwnIndex) in fileInfoOwns" :key="fileInfoOwnIndex">
          <el-card :body-style="{ padding: '0px' }">
            <a :href="fileInfoOwn.fileInfo.url" target="_blank">
              <img :src="fileInfoOwn.fileInfo.url" class="image" style="width: 100%; display: block;">
            </a>
            <el-alert title="md5 " type="info" :closable="false">
              <code v-text="fileInfoOwn.fileInfo.md5"></code>
            </el-alert>
            <el-alert title="url " type="info" :closable="false">
              <code v-text="fileInfoOwn.fileInfo.url"></code>
            </el-alert>
            <el-alert title="size " type="info" :closable="false">
              <code v-text="fileInfoOwn.fileInfo.fileLength"></code>
            </el-alert>
            <el-alert title="type " type="info" :closable="false">
              <code v-text="fileInfoOwn.fileInfo.contentType"></code>
            </el-alert>
            <el-alert title="time " type="info" :closable="false">
              <code v-text="fileInfoOwn.fileInfo.createTime"></code>
            </el-alert>
            <el-alert title="" type="info" :closable="false" v-if="fileInfoOwn.owns!=null">
              <el-tag v-for="own in fileInfoOwn.owns">
                <a @click="openDialog(own)" v-text="own.username"></a>
              </el-tag>
            </el-alert>
          </el-card>
        </el-col>
      </el-row>

      <el-dialog title="文件所属" :visible.sync="dialogVisible" :before-close="closeDialog">
        <el-form label-position="right">
          <el-alert title="md5 " type="warning" :closable="false">
            <code v-text="own.md5"></code>
          </el-alert>
          <el-alert title="账号 " type="warning" :closable="false">
            <code v-text="own.username"></code>
          </el-alert>
          <el-form-item label="文件名">
            <el-input :value="own.fileName" v-model="own.fileName" placeholder="文件名"></el-input>
          </el-form-item>
          <el-form-item label="分类">
            <el-input :value="own.sort" v-model="own.sort" placeholder="分类"></el-input>
          </el-form-item>
          <el-form-item label="描述">
            <el-input type="textarea" :value="own.description" v-model="own.description" placeholder="描述"></el-input>
          </el-form-item>
          <el-alert title="创建时间 " type="info" :closable="false">
            <code v-text="own.createTime"></code>
          </el-alert>
          <el-alert title="更新时间 " type="info" :closable="false">
            <code v-text="own.updateTime"></code>
          </el-alert>
          <el-alert title="文件大小 " type="info" :closable="false">
            <code v-text="own.fileLength"></code>
          </el-alert>
          <el-alert title="文件类型 " type="info" :closable="false">
            <code v-text="own.contentType"></code>
          </el-alert>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="danger" @click="removeOwn">删除所属</el-button>
          <el-button type="info" @click="closeDialog">关闭</el-button>
        </div>
      </el-dialog>
    </el-main>
    <el-footer>
      <pagination :total="total" v-on:changePageSize="changePageSize" v-on:turnPage="turnPage"></pagination>
    </el-footer>
  </el-container>
</template>

<script>
  import api from '../utils/api'
  import util from '../utils/util'
  import pagination from './pagination'

  export default {
    name: "admin-file-info-own",
    data() {
      return {
        total: 0,
        columnCount: 2,
        columnCounts: [1, 2, 3, 4, 6],
        fileInfoOwns: [],
        fileInfoOwnQuery: {
          pageSize: 10,
          page: 1,
          fileId: 0,
          md5: null,
          fileLength: 0,
          contentType: null,
          createTime: null,
          updateTime: null
        },
        dialogVisible: false,
        own: {
          fileName: null,
          sort: null,
          description: null,
          createTime: null,
          updateTime: null,
          username: null,
          md5: null,
          fileLength: null,
          contentType: null,
        },
      }
    },
    created: function () {
      this.listFileInfoOwn()
      api.getFileInfoCount(
        this.fileInfoOwnQuery.pageSize,
        this.fileInfoOwnQuery.page,
        this.fileInfoOwnQuery.fileId,
        this.fileInfoOwnQuery.md5,
        this.fileInfoOwnQuery.fileLength,
        this.fileInfoOwnQuery.contentType,
        this.fileInfoOwnQuery.createTime,
        this.fileInfoOwnQuery.updateTime)
        .then(res => {
          if (res.data.status != 1) {
            this.$message.error(res.data.massage)
            return;
          }
          this.total = res.data.data;
        })
    },
    methods: {
      changePageSize: function (pageSize) {
        this.fileInfoOwnQuery.pageSize = pageSize
      },
      turnPage: function (page) {
        this.fileInfoOwnQuery.page = page
        this.listFileInfoOwn()
      },
      removeOwn: function () {
        api.removeOwn(this.own.ownId)
          .then(res => {
            if (res.data.status != 1) {
              this.$message.error(res.data.massage)
              return;
            }
            this.$message.success('删除成功')
            this.dialogVisible = false;
            this.own = {
              fileName: null,
              sort: null,
              description: null,
              createTime: null,
              updateTime: null,
              username: null,
              md5: null,
              fileLength: null,
              contentType: null,
            };
          })
      },
      openDialog: function (own) {
        this.own = own;
        this.dialogVisible = true;
      },
      closeDialog: function () {
        this.own = {
          fileName: null,
          sort: null,
          description: null,
          createTime: null,
          updateTime: null,
          username: null,
          md5: null,
          fileLength: null,
          contentType: null,
        };
        this.dialogVisible = false;
      },
      listFileInfoOwn: function () {
        api.listFileInfoOwn(
          this.fileInfoOwnQuery.pageSize,
          this.fileInfoOwnQuery.page,
          this.fileInfoOwnQuery.fileId,
          this.fileInfoOwnQuery.md5,
          this.fileInfoOwnQuery.fileLength,
          this.fileInfoOwnQuery.contentType,
          this.fileInfoOwnQuery.createTime,
          this.fileInfoOwnQuery.updateTime)
          .then(res => {
            if (res.data.status != 1) {
              this.$message.error(res.data.massage)
              return;
            }
            this.fileInfoOwns = res.data.data;
            for (let i = 0; i < this.fileInfoOwns.length; i++) {
              this.fileInfoOwns[i].fileInfo.createTime = util.formatTimestamp(this.fileInfoOwns[i].fileInfo.createTime, 'yyyy-MM-dd hh:mm:ss')
              this.fileInfoOwns[i].fileInfo.fileLength = util.formatFileSize(this.fileInfoOwns[i].fileInfo.fileLength)
              for (let j = 0; j < this.fileInfoOwns[i].owns.length; j++) {
                this.fileInfoOwns[i].owns[j].createTime = util.formatTimestamp(this.fileInfoOwns[i].owns[j].createTime, 'yyyy-MM-dd hh:mm:ss')
                this.fileInfoOwns[i].owns[j].updateTime = util.formatTimestamp(this.fileInfoOwns[i].owns[j].updateTime, 'yyyy-MM-dd hh:mm:ss')
                this.fileInfoOwns[i].owns[j].fileLength = util.formatFileSize(this.fileInfoOwns[i].owns[j].fileLength)
              }
            }
          })
      }
    },
    components: {
      'pagination': pagination,
    },
  }
</script>

<style scoped>

</style>
