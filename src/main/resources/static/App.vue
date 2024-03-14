<template>
    <nav class="navbar navbar-expand-lg bg-body-tertiary">
        <div class="container-fluid">
            <a class="navbar-brand" href="#/">Orofacial Pain Management</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#/ ">Start</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#/input">Eingabe</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#/overview">Überblick</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <component :is="currentView" />
</template>

<script setup>
import { ref, computed } from 'vue';
import Home from './Home.vue'
import Input from './Input.vue'
import Overview from './Overview.vue'


const countermeasuresOptions = ref([]);

const routes = {
    '/': Home,
    '/input': Input,
    '/overview': Overview,
}

const currentPath = ref(window.location.hash)

window.addEventListener('hashchange', () => {
    currentPath.value = window.location.hash
})

const currentView = computed(() => {
    return routes[currentPath.value.slice(1) || '/'] || NotFound
})
</script>