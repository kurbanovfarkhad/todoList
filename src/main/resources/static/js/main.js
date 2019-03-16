var taskApi = Vue.resource('taskList{/id}');

function getindex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i
        }
    }
    return -1;
}

Vue.component('task-form', {
    props: ['tasks', 'taskAttr'],
    data: function () {
        return {
            task: '',
            date: null,
            checker: true,
            id: null
        }
    },
    watch: {
        taskAttr: function (newVal, oldVal) {
            this.task = newVal.task;
            this.date = newVal.date;
            this.checker = newVal.checker;
            this.id = newVal.id;
        }
    },
    template: '<div>' +
    '<input type="text" placeholder="write your task" v-model="task">' +
    '<input type="date" value="2013-01-08" v-model="date">' +
    '<input type="checkbox" v-model="checker">' +
    '<input type="button" value="Save" @click="save">' +
    '</div>',
    methods: {
        save: function () {
            var task = {task: this.task, date: this.date, checker: this.checker};
            if (this.id) {
                console.log(task);

                taskApi.update({id: this.id}, task).then(result =>
                    result.json().then(data => {
                        var index = getindex(this.tasks, data.id);
                        this.tasks.splice(index, 1, data);
                        console.log(data);
                        this.task = '';
                        this.id = '';
                        this.date = '';
                    })
                )
            } else {
                taskApi.save({}, task).then(result => result.json().then(data => {
                        this.tasks.push(data);
                        this.task = '';
                        this.id = '';
                        this.date = '';
                    }
                ))
            }
        }
    }
});


Vue.component('task-row', {
    props: ['task', 'editMethod', 'tasks'],
    template: '<div>' +
    '<b>{{task.id}}</b>{{task.task}}{{task.date}}' +
    '<p v-if="task.checker===true">Everything okay</p>' +
    '<p v-else="task.checker===true">all bad</p>' +
    '<span>' +
    '<input type="button" value="edit" @click="edit">' +
    '<input type="button" value="delete" @click="del">' +
    '</span>' +
    '</div>',
    methods: {
        edit: function () {
            this.editMethod(this.task);
        },
        del: function () {
            taskApi.remove({id: this.task.id}).then(result => {
                if (result.ok) {
                    this.tasks.splice(this.tasks.indexOf(this.task), 1)
                }
            })
        }
    }

});


Vue.component('tasks-list', {
    props: ['tasks'],
    data: function () {
        return {
            task: null
        }
    },
    template: '<div>' +
    '<task-form :tasks="tasks" :taskAttr="task"/>' +
    '<h3>List</h3>' +
    '<task-row v-for="task in tasks" :task="task" :key="task.id" :editMethod="editMethod" :tasks="tasks"/>' +
    '</div>',
    methods: {
        editMethod: function (task) {
            this.task = task;
        }
    }
});


var app = new Vue({
    el: '#app',
    template: '<div>' +
    '<div v-if="!profile">login <a href="/login">Push</a></div>' +
    '<div  v-else>' +
    '<div>{{profile.name}}&nbsp;<a href="/logout">logout</a></div>' +
    '<tasks-list :tasks="tasks"/>' +
    '</div>' +

    '</div>',
    data: {
        tasks: frontendData.tasks,
        profile: frontendData.profile
    },
    created: function () {
        // taskApi.get().then(result => result.json().then(data => data.forEach(task => this.tasks.push(task))));
    }
});
