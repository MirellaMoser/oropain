<template>
    <!-- Container for overview and modal -->
    <div>
      <!-- Overview Card -->
      <div class="container-fluid pt-3">
        <div class="card">
          <!-- Overview Card Header -->
          <div class="card-header" style="background-color: grey;">
            <li class="list-group-item d-flex justify-content-between align-items-center">
              Meine Angaben
              <span>{{ overview.dateOfEntry }}</span>
            </li>
          </div>
          <!-- Overview Card Body -->
          <ul class="list-group list-group-flush">
            <!-- Time of Entry -->
            <li class="list-group-item d-flex justify-content-between align-items-center">
              Zeitpunkt
              <span v-if="overview.timeOfEntry === 'UNSET'">Keine Angabe</span>
              <span v-else-if="overview.timeOfEntry === 'MORNING'">Morgen</span>
              <span v-else-if="overview.timeOfEntry === 'AFTERNOON'">Nachmittag</span>
              <span v-else-if="overview.timeOfEntry === 'EVENING'">Abend</span>
              <span v-else-if="overview.timeOfEntry === 'NIGHT'">Nacht</span>
            </li>
            <!-- Pain Intensity -->
            <li class="list-group-item d-flex justify-content-between align-items-center">
              Schmerz-Intensität
              <span>{{ overview.intensity }}</span>
            </li>
            <!-- Symptoms -->
            <li class="list-group-item d-flex justify-content-between align-items-center">
              Symptome
              <span>{{ overview.symptoms }}</span>
            </li>
            <!-- Stress Level -->
            <li class="list-group-item d-flex justify-content-between align-items-center">
              Stress-Level
              <span>{{ overview.stressLevel }}</span>
            </li>
            <!-- Stressors -->
            <li class="list-group-item d-flex justify-content-between align-items-center">
              Stressoren
              <span>{{ overview.stressors }}</span>
            </li>
          </ul>
        </div>
        <!-- Tree Images -->
        <div style="position:relative; text-align: center; margin-top: 20px;">
          <div style="position: absolute; left: 50%; top: 0px; transform: translateX(-50%);">
            <a href="#" data-bs-toggle="modal" data-bs-target="#baumerweiterung">
              <img :src="imagePath" style="width: 100%; max-width: 100%;">
            </a>
          </div>
          <div style="position: absolute; left: 50%; top: 0px; transform: translateX(-50%);">
            <a href="#" data-bs-toggle="modal" data-bs-target="#baumerweiterung">
              <img :src="overlayPath" style="width: 100%; max-width: 100%;">
            </a>
          </div>
        </div>
      </div>
  
      <!-- Modal for adding countermeasures -->
      <div class="modal fade" id="baumerweiterung" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h1 class="modal-title fs-5" id="exampleModalLabel">Lebensbaum erweitern und Maßnahmen hinzufügen</h1>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <!-- Countermeasures checkboxes -->
              <div class="form-check" v-for="cm in countermeasuresOptions" :key="cm.name">
                <input class="form-check-input" type="checkbox" v-model="cm.selected" id="flexCheckDefault">
                <label class="form-check-label" for="flexCheckDefault">{{ cm.name }}</label>
              </div>
            </div>
            <div class="modal-footer">
              <!-- Close and Save buttons -->
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Schließen</button>
              <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="saveCountermeasures">Speichern</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  
  // Initialize reactive variables
  const countermeasuresOptions = ref([]);
  const overview = ref([]);
  const imagePath = ref();
  const overlayPath = ref();
  
  // Fetch data from APIs on component mount
  onMounted(() => {
    axios.get('/api/record/current/countermeasures').then(response => {
      countermeasuresOptions.value = response.data;
      updateOverviewImage();
    });
    axios.get('/api/record/current/overview').then(response => {
      overview.value = response.data;
      updateOverviewImage();
      console.log(response.data);
    });
  });
  
  // Function to save selected countermeasures
  const saveCountermeasures = () => {
    axios.post('/api/record/current/countermeasures', countermeasuresOptions.value).then(response => {
      updateOverviewImage();
    });
  };
  
  // Function to update tree images based on overview data
  const updateOverviewImage = () => {
    var selectedCms = 0;
    var intensityRounded = Math.round(overview.value.intensity);
  
    // Count selected countermeasures
    for (const cm of countermeasuresOptions.value) {
      if (cm.selected) selectedCms++;
    }
    if (selectedCms > 3) selectedCms = 3;
  
    // Update image paths based on stress level
    switch (overview.value.stressLevel) {
      case "kein":
        imagePath.value = `/img/stress_null/kein_${intensityRounded}.svg`;
        overlayPath.value = `/img/stress_null/overlay_Stufe${selectedCms}.svg`;
        break;
      case "tief":
        imagePath.value = `/img/stress_small/tief_${intensityRounded}.svg`;
        overlayPath.value = `/img/stress_small/overlay_Stufe${selectedCms}.svg`;
        break;
      case "mittel":
        imagePath.value = `/img/stress_medium/mittel_${intensityRounded}.svg`;
        overlayPath.value = `/img/stress_medium/overlay_Stufe${selectedCms}.svg`;
        break;
      default:
        imagePath.value = `/img/stress_high/hoch_${intensityRounded}.svg`;
        overlayPath.value = `/img/stress_high/overlay_Stufe${selectedCms}.svg`;
        break;
    }
  };
  </script>