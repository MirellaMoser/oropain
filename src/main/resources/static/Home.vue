<template>
    <div class="container-fluid pt-3">
        <div class="card">
            <div class="card-header">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Meine Angaben
                    <span>01.03.2024</span>
                </li>
            </div>
            <ul class="list-group list-group-flush">
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Schmerz-Intensität
                    <span>2</span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Symptome
                    <span>keine</span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Stress-Level
                    <span>tief</span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Stressoren
                    <span>Zeitverlust durch Stau</span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Zeitpunkt
                    <span>Mittag</span>
                </li>
            </ul>
            <a href="#" data-bs-toggle="modal" data-bs-target="#baumerweiterung">
                <img src="img/Element 1@2x.png" class="card-img-top" alt="..."></a>
        </div>
    </div>

    <div class="modal fade" id="baumerweiterung" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="exampleModalLabel">Lebensbaum erweitern und Massnahmen hinzufügen</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="form-check" v-for="cm in countermeasuresOptions">
                        <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault">
                        <label class="form-check-label" for="flexCheckDefault">
                            {{ cm.name }}
                        </label>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save changes</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref,onMounted } from 'vue';


export default {
    setup() {

        const countermeasuresOptions = ref([]);

        onMounted(() => {
            axios.get('/countermeasures').then(response => {
                countermeasuresOptions.value = response.data;
            });
        });

        return {
            countermeasuresOptions
        }
    },

}
</script>