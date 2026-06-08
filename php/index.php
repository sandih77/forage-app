<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Suivi des Alertes — ForageApp</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background-color: #0f1117;
            color: #f1f5f9;
            font-family: 'Segoe UI', sans-serif;
        }

        .card {
            background-color: #191d27;
            border: 1px solid #2e3447;
            border-radius: 12px;
        }

        /* Styles Tableaux Corrigés (Thème Sombre) */
        .table-wrapper {
            border-radius: 10px;
            overflow: hidden;
            border: 1px solid #2e3447;
        }

        .table {
            margin: 0;
            color: #f1f5f9;
        }

        .table thead th {
            background-color: #222736;
            border-bottom: 2px solid #2e3447;
            color: #94a3b8;
            font-size: .75rem;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: .08em;
            padding: 1rem 1.25rem;
        }

        .table tbody tr {
            border-bottom: 1px solid #252a38;
            background: transparent !important;
        }

        .table tbody tr:hover {
            background-color: #222736 !important;
        }

        .table tbody td {
            padding: .95rem 1.25rem;
            vertical-align: middle;
            font-size: .9rem;
            color: #f1f5f9 !important;
            border: none;
            background: transparent !important;
        }

        .badge-id {
            background: #222736;
            color: #94a3b8;
            border: 1px solid #2e3447;
            border-radius: 6px;
            padding: .2rem .55rem;
            font-family: monospace;
        }

        .badge-ref {
            background: rgba(45, 212, 191, 0.15);
            color: #2dd4bf;
            border: 1px solid rgba(45, 212, 191, 0.3);
            border-radius: 20px;
            padding: .25rem .75rem;
            font-weight: 600;
        }

        /* Boutons */
        .btn-outline-accent {
            background: transparent;
            border: 1.5px solid #2dd4bf;
            color: #2dd4bf;
            border-radius: 8px;
            font-size: 0.85rem;
            transition: all 0.2s;
        }

        .btn-outline-accent:hover {
            background: rgba(45, 212, 191, 0.18);
            color: #5eead4;
            border-color: #5eead4;
        }

        /* Fenêtre Modale Custom Sombre */
        .modal-content {
            background-color: #191d27;
            border: 1px solid #2e3447;
            color: #f1f5f9;
            border-radius: 12px;
        }

        .modal-header {
            border-bottom: 1px solid #2e3447;
        }

        .modal-footer {
            border-top: 1px solid #2e3447;
        }

        /* Boîtes d'alertes */
        .alerte-item {
            background-color: #222736;
            border: 1px solid #2e3447;
            border-radius: 8px;
            padding: 1.25rem;
            margin-bottom: 1rem;
        }

        .txt-secondary {
            color: #94a3b8;
            font-size: 0.9rem;
        }

        .hidden {
            display: none !important;
        }
    </style>
</head>

<body>

    <div class="container py-5">

        <div class="text-center mb-5">
            <h1 style="font-family: serif;">ForageApp <span style="color: #2dd4bf;">•</span> Tableau de Bord</h1>
            <p class="txt-secondary">Visualisez l'état global et les alertes de traitement par demande en temps réel</p>
        </div>

        <div id="mainErrorBlock" class="alert alert-danger d-flex align-items-center hidden" role="alert">
            <i class="bi bi-exclamation-triangle-fill me-2"></i>
            <div id="mainErrorMessage"></div>
        </div>

        <div class="card shadow p-2">
            <div class="table-responsive table-wrapper">
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Client</th>
                            <th>Commune</th>
                            <th>Lieu</th>
                            <th>Référence</th>
                            <th class="text-end">Actions</th>
                        </tr>
                    </thead>
                    <tbody id="demandesTableBody">
                        <tr>
                            <td colspan="6" class="text-center py-5">
                                <div class="spinner-border text-info" role="status"></div>
                                <p class="mt-2 txt-secondary mb-0">Chargement des demandes...</p>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </div>

    <div class="modal fade" id="alertesModal" tabindex="-1" aria-labelledby="alertesModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="alertesModalLabel" style="color: #2dd4bf;">
                        <i class="bi bi-shield-exclamation me-2"></i>Analyse des retards : <span id="modalRefTitle" class="text-white"></span>
                    </h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div id="modalSpinner" class="text-center py-4 hidden">
                        <div class="spinner-border text-info" role="status"></div>
                    </div>
                    <div id="modalAlertesContainer"></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary btn-sm" data-bs-dismiss="modal">Fermer</button>
                </div>
            </div>
        </div>
    </div>

    <template id="templateRow">
        <tr>
            <td><span class="badge-id item-id"></span></td>
            <td class="item-client"></td>
            <td class="item-commune"></td>
            <td class="item-lieu"></td>
            <td><span class="badge-ref item-ref"></span></td>
            <td class="text-end">
                <button type="button" class="btn btn-outline-accent btn-sm btn-voir-alertes">
                    <i class="bi bi-bell-fill"></i> Voir Alertes
                </button>
            </td>
        </tr>
    </template>

    <template id="templateEmptyAlertes">
        <div class="text-center py-4">
            <i class="bi bi-check-circle-fill text-success fs-1 mb-2"></i>
            <p class="mb-0 fw-bold text-success">Aucun retard détecté !</p>
            <p class="txt-secondary small mb-0">Toutes les étapes respectent les délais impartis pour cette demande.</p>
        </div>
    </template>

    <template id="templateAlerteItem">
        <div class="alerte-item">
            <div class="d-flex justify-content-between align-items-start">
                <h5 class="h6 mb-2 fw-bold text-white transition-title">
                    <i class="bi bi-arrow-right-circle-fill text-muted me-2"></i>
                </h5>
                <span class="badge bg-danger rounded-pill px-2 py-1 depassement-badge" style="font-size: 0.75rem;"></span>
            </div>
            <div class="row g-2 mt-1 txt-secondary">
                <div class="col-6">
                    <small>Temps max alloué :</small> <span class="text-white fw-medium limite-field"></span>
                </div>
                <div class="col-6">
                    <small>Temps réel consommé :</small> <span class="text-white fw-medium reelle-field"></span>
                </div>
            </div>
        </div>
    </template>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            const demandesTableBody = document.getElementById("demandesTableBody");
            const mainErrorBlock = document.getElementById("mainErrorBlock");
            const mainErrorMessage = document.getElementById("mainErrorMessage");

            const modalRefTitle = document.getElementById("modalRefTitle");
            const modalSpinner = document.getElementById("modalSpinner");
            const modalAlertesContainer = document.getElementById("modalAlertesContainer");

            const alertesModal = new bootstrap.Modal(document.getElementById('alertesModal'));

            const baseUrl = "http://localhost:8080/forage-app";

            fetch(`${baseUrl}/demande/api/listAll`)
                .then(res => {
                    if (!res.ok) throw new Error("Impossible de joindre le serveur Spring Boot.");
                    return res.json();
                })
                .then(demandes => {
                    demandesTableBody.innerHTML = ""; 

                    if (demandes.length === 0) {
                        demandesTableBody.innerHTML = `<tr><td colspan="6" class="text-center py-4 txt-secondary">Aucune demande enregistrée.</td></tr>`;
                        return;
                    }

                    const templateRow = document.getElementById("templateRow");

                    demandes.forEach(demande => {
                        const clone = templateRow.content.cloneNode(true);

                        clone.querySelector(".item-id").innerText = demande.id;
                        clone.querySelector(".item-client").innerText = demande.clientNom || "Aucun client";
                        clone.querySelector(".item-commune").innerText = demande.communeNom || "Non renseignée";
                        clone.querySelector(".item-lieu").innerText = demande.lieu || "—";
                        clone.querySelector(".item-ref").innerText = demande.reference;

                        clone.querySelector(".btn-voir-alertes").addEventListener("click", function() {
                            ouvrirModaleAlertes(demande.id, demande.reference);
                        });

                        demandesTableBody.appendChild(clone);
                    });
                })
                .catch(err => {
                    mainErrorMessage.innerText = "Erreur lors de la récupération de la liste : " + err.message;
                    mainErrorBlock.classList.remove("hidden");
                    demandesTableBody.innerHTML = `<tr><td colspan="6" class="text-center py-4 text-danger">Échec du chargement.</td></tr>`;
                });

            function ouvrirModaleAlertes(id, reference) {
                modalRefTitle.innerText = reference;
                modalAlertesContainer.innerHTML = "";
                modalSpinner.classList.remove("hidden");

                alertesModal.show();

                fetch(`${baseUrl}/demande/${id}/alertes`)
                    .then(res => {
                        if (!res.ok) throw new Error("Erreur lors de l'extraction des données d'alertes.");
                        return res.json();
                    })
                    .then(alertes => {
                        modalSpinner.classList.add("hidden");

                        if (alertes.length === 0) {
                            const tplEmpty = document.getElementById("templateEmptyAlertes").content.cloneNode(true);
                            modalAlertesContainer.appendChild(tplEmpty);
                        } else {
                            const templateAlerteItem = document.getElementById("templateAlerteItem");

                            alertes.forEach(alt => {
                                const cloneAlt = templateAlerteItem.content.cloneNode(true);

                                const box = cloneAlt.querySelector(".alerte-item");
                                box.style.borderLeft = `5px solid ${alt.couleur || '#f87171'}`;

                                cloneAlt.querySelector(".transition-title").innerHTML += alt.transition;
                                cloneAlt.querySelector(".depassement-badge").innerText = `Retard : ${alt.depassement}`;
                                cloneAlt.querySelector(".limite-field").innerText = alt.dureeLimite;
                                cloneAlt.querySelector(".reelle-field").innerText = alt.dureeReelle;

                                modalAlertesContainer.appendChild(cloneAlt);
                            });
                        }
                    })
                    .catch(err => {
                        modalSpinner.classList.add("hidden");
                        modalAlertesContainer.innerHTML = `<div class="alert alert-danger mb-0">${err.message}</div>`;
                    });
            }
        });
    </script>
</body>

</html>