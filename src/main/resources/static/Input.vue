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
                    <span><select class="form-select" aria-label="Default select example">
                            <option selected>keine Angabe</option>
                            <option value="1">0</option>
                            <option value="2">1</option>
                            <option value="3">2</option>
                            <option value="3">3</option>
                            <option value="3">4</option>
                            <option value="3">5</option>
                            <option value="3">6</option>
                            <option value="3">7</option>
                            <option value="3">8</option>
                            <option value="3">9</option>
                            <option value="3">10</option>
                        </select></span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Symptome
                    <span>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault">
                            <label class="form-check-label" for="flexCheckDefault">
                                Nackenschmerzen
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault">
                            <label class="form-check-label" for="flexCheckDefault">
                                Kieferschmerzen
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault">
                            <label class="form-check-label" for="flexCheckDefault">
                                Brennende Zunge
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault">
                            <label class="form-check-label" for="flexCheckDefault">
                                Migräne
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault">
                            <label class="form-check-label" for="flexCheckDefault">
                                Zahnschmerzen
                            </label>
                        </div>
                    </span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Stress-Level
                    <span><select class="form-select" aria-label="Default select example">
                            <option selected>keine Angabe</option>
                            <option value="1">ohne</option>
                            <option value="2">tief</option>
                            <option value="3">mittel</option>
                            <option value="3">hoch</option>
                        </select></span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Stressoren
                    <span>Zeitverlust durch Stau</span>
                </li>
                <li class="list-group-item d-flex justify-content-between align-items-center">
                    Zeitpunkt
                    <span><select class="form-select" aria-label="Default select example">
                            <option selected>keine Angabe</option>
                            <option value="1">Morgen</option>
                            <option value="2">Mittag</option>
                            <option value="3">Abend</option>
                        </select></span>
                </li>
            </ul>
            <a href="#" data-bs-toggle="modal" data-bs-target="#baumerweiterung"></a>
        </div>
    </div>
</template>