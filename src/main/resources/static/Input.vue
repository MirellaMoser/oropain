<template>
    <div class="container-fluid pt-3">
        <div class="card">
            <div class="card-header">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Meine Angaben
                    <input type="date" class="form-control" id="dateInput" name="date" value="2024-03-01">
                </li>
            </div>
            <ul class="list-group list-group-flush">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Schmerz-Intensität
                    <span>
                        <select class="form-select" aria-label="Default select example" v-model="selectionModel.intensity">
                            <option value="-1">keine Angabe</option>
                            <option value="0">0</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>
                        </select>
                    </span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Symptome
                    <span>
                        <div class="form-check" v-for="cm in selectionModel.symptoms">
                            <input class="form-check-input" type="checkbox" v-model="cm.selected" id="flexCheckDefault">
                            <label class="form-check-label" for="flexCheckDefault">
                                {{ cm.name }}
                            </label>
                        </div>
                    </span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Stress-Level
                    <span>
                        <select class="form-select" aria-label="Default select example" v-model="selectionModel.stressLevel">
                            <option value="-1">keine Angabe</option>
                            <option value="0">0</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>
                        </select>
                    </span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Stressoren
                    <span>
                        <div class="form-check" v-for="cm in selectionModel.stressors">
                            <input class="form-check-input" type="checkbox" v-model="cm.selected" id="flexCheckDefault">
                            <label class="form-check-label" for="flexCheckDefault">
                                {{ cm.name }}
                            </label>
                        </div>
                    </span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Zeitpunkt
                    <span><select class="form-select" v-model="selectionModel.timeOfDay" aria-label="Default select example">
                            <option value="UNSET">keine Angabe</option>
                            <option value="MORNING">Morgen</option>
                            <option value="AFTERNOON">Nachmittag</option>
                            <option value="EVENING">Abend</option>
                            <option value="NIGHT">Nacht</option>
                        </select></span>
                </li>
            </ul>
            <div class="card-footer">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    <button type="button" class="btn btn-primary" @click="saveSituation">Save changes</button>
                </li>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const selectionModel = ref([]);

onMounted(() => {
    axios.get('/api/situation/new/empty').then(response => {
        selectionModel.value = response.data;
    });
});

const saveSituation = () => {
    axios.post('/api/situation', selectionModel.value).then(response => {
        window.location.href = '#/';
    });
};

</script>