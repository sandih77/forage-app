document.addEventListener("DOMContentLoaded", function () {
    const referenceInput = document.getElementById("referenceInput");
    const demandeBlock = document.getElementById("demandeBlock");
    const typeDevisSelect = document.getElementById("typeDevis");
    const lignesContainer = document.getElementById("lignesContainer");
    const btnAddLigne = document.getElementById("btnAddLigne");
    const template = document.getElementById("ligneDevisTemplate");
    const mainForm = document.getElementById("mainDevisForm");
    const formContent = document.getElementById("formContent");
    const demandeIdInput = document.getElementById("demandeIdInput");

    if (!mainForm || !lignesContainer || !btnAddLigne || !template) {
        return;
    }

    const setVisible = (element, visible) => {
        if (!element) return;
        element.classList.toggle("hidden", !visible);
    };

    let compteurLigne = 0;
    const isEditMode = /\/devis\/edit\//.test(window.location.href) || /\/devis\/update\//.test(mainForm.action);

    if (isEditMode) {
        chargerDetailsExistants();
    }

    function chargerDetailsExistants() {
        const match = window.location.href.match(/\/devis\/edit\/(\d+)/) || mainForm.action.match(/\/devis\/update\/(\d+)/);

        if (!match) {
            console.error("Impossible de récupérer l'ID du devis pour le chargement des détails.");
            return;
        }

        const devisId = match[1];

        fetch(`/forage-app/devis/details/${devisId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Erreur serveur HTTP " + response.status);
                }
                return response.json();
            })
            .then(details => {
                console.log("Détails chargés avec succès :", details);

                if (Array.isArray(details) && details.length > 0) {
                    lignesContainer.innerHTML = "";
                    details.forEach(detail => {
                        ajouterLigneEdition(detail);
                    });
                }
                setVisible(formContent, true);
                gererVisibiliteBoutonEnregistrer();
            })
            .catch(error => console.error("Erreur lors du chargement des détails :", error));
    }

    function ajouterLigneEdition(detail) {
        const clone = template.content.cloneNode(true);
        const divLigne = clone.querySelector(".ligne-devis");
        divLigne.id = "ligne_" + compteurLigne;

        const inputDesignation = clone.querySelector(".designation");
        const inputDescription = clone.querySelector(".description");
        const inputQty = clone.querySelector(".quantite");
        const inputPu = clone.querySelector(".prixUnitaire");
        const inputTotal = clone.querySelector(".ligne-total-field");

        inputDesignation.value = detail.designation || "";
        inputDescription.value = detail.description || "";

        const qte = detail.quantite || detail.quantity || 1;
        const pu = detail.prixUnitaire || 0;

        inputQty.value = qte;
        inputPu.value = pu.toFixed(2);
        inputTotal.value = (qte * pu).toFixed(2);

        inputDesignation.name = `lignes[${compteurLigne}].designation`;
        inputDescription.name = `lignes[${compteurLigne}].description`;
        inputQty.name = `lignes[${compteurLigne}].quantite`;
        inputPu.name = `lignes[${compteurLigne}].prixUnitaire`;
        inputTotal.name = `lignes[${compteurLigne}].montant`;

        function calculerMontant() {
            const q = parseFloat(inputQty.value) || 0;
            const p = parseFloat(inputPu.value) || 0;
            inputTotal.value = (q * p).toFixed(2);
        }

        inputQty.addEventListener("input", calculerMontant);
        inputPu.addEventListener("input", calculerMontant);

        clone.querySelector(".btn-remove-ligne").addEventListener("click", function () {
            divLigne.remove();
            gererVisibiliteBoutonEnregistrer();
        });

        lignesContainer.appendChild(clone);
        compteurLigne++;
    }

    if (referenceInput) {
        referenceInput.addEventListener("input", function () {
            const refValue = referenceInput.value.trim();

            if (refValue.length < 3) {
                masquerTout();
                return;
            }

            const url = referenceInput.getAttribute("data-url");

            fetch(`${url}?reference=${encodeURIComponent(refValue)}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Erreur serveur HTTP " + response.status);
                    }
                    return response.json();
                })
                .then(data => {
                    if (data.found) {
                        console.log("Demande trouvée :", data);

                        if (demandeIdInput) demandeIdInput.value = data.id;

                        document.getElementById("txtLieu").innerText = data.lieu || "—";
                        document.getElementById("txtReference").innerText = data.reference || "—";
                        document.getElementById("txtClient").innerText = data.clientNom || "—";
                        document.getElementById("txtCommune").innerText = data.communeNom || "—";

                        setVisible(demandeBlock, true);

                        if (typeDevisSelect && Array.isArray(data.typeDevis) && data.typeDevis.length > 0) {
                            typeDevisSelect.innerHTML = "";
                            data.typeDevis.forEach(td => {
                                const option = document.createElement("option");
                                option.value = td.id;
                                option.textContent = td.nom;
                                typeDevisSelect.appendChild(option);
                            });

                            setVisible(formContent, true);
                        } else {
                            setVisible(formContent, false);
                        }
                    } else {
                        masquerTout();
                    }
                })
                .catch(error => {
                    console.error("Erreur lors du Fetch AJAX :", error);
                });
        });
    }

    btnAddLigne.addEventListener("click", function () {
        const clone = template.content.cloneNode(true);
        const divLigne = clone.querySelector(".ligne-devis");
        divLigne.id = "ligne_" + compteurLigne;

        clone.querySelector(".designation").name = `lignes[${compteurLigne}].designation`;
        clone.querySelector(".description").name = `lignes[${compteurLigne}].description`;
        clone.querySelector(".quantite").name = `lignes[${compteurLigne}].quantite`;
        clone.querySelector(".prixUnitaire").name = `lignes[${compteurLigne}].prixUnitaire`;
        clone.querySelector(".ligne-total-field").name = `lignes[${compteurLigne}].montant`;

        const inputQty = clone.querySelector(".quantite");
        const inputPu = clone.querySelector(".prixUnitaire");
        const inputTotal = clone.querySelector(".ligne-total-field");

        function calculerMontant() {
            const qte = parseFloat(inputQty.value) || 0;
            const pu = parseFloat(inputPu.value) || 0;
            inputTotal.value = (qte * pu).toFixed(2);
        }
        inputQty.addEventListener("input", calculerMontant);
        inputPu.addEventListener("input", calculerMontant);

        clone.querySelector(".btn-remove-ligne").addEventListener("click", function () {
            divLigne.remove();
            gererVisibiliteBoutonEnregistrer();
        });

        lignesContainer.appendChild(clone);
        compteurLigne++;
        gererVisibiliteBoutonEnregistrer();
    });

    function gererVisibiliteBoutonEnregistrer() {
        const nbLignes = lignesContainer.querySelectorAll(".ligne-devis").length;

        if (isEditMode) {
            setVisible(formContent, true);
        } else {
            const hasDemande = demandeIdInput && demandeIdInput.value !== "";
            setVisible(formContent, hasDemande || nbLignes > 0);
        }
    }

    function masquerTout() {
        if (demandeIdInput) demandeIdInput.value = "";
        setVisible(demandeBlock, false);
        setVisible(formContent, false);
        if (typeDevisSelect) {
            typeDevisSelect.innerHTML = "";
        }
        lignesContainer.innerHTML = "";
        compteurLigne = 0;
    }
});