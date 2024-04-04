<template>
    <div>
        <!-- Navbar -->
        <nav class="navbar navbar-expand-lg bg-body-tertiary">
            <div class="container-fluid">
                <a class="navbar-brand" href="#/">Orofacial Pain Management</a>
                <!-- Navbar toggler button -->
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                    aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <!-- Navbar collapse section -->
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <!-- Navbar items -->
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="#/">Start</a>
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
        <!-- Component rendering -->
        <component :is="currentView" />
    </div>
</template>

<script setup>
// Importing necessary modules and components
import { ref, computed } from 'vue';
import Home from './Home.vue'
import Input from './Input.vue'
import Overview from './Overview.vue'

// Reference to the countermeasures options
const countermeasuresOptions = ref([]);

// Route mapping
const routes = {
    '/': Home,
    '/input': Input,
    '/overview': Overview,
}

// Reference to the current path
const currentPath = ref(window.location.hash)

// Event listener for hashchange
window.addEventListener('hashchange', () => {
    currentPath.value = window.location.hash
})

// Computed property for current view
const currentView = computed(() => {
    return routes[currentPath.value.slice(1) || '/'] || NotFound
})
</script>