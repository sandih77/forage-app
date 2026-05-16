document.addEventListener("DOMContentLoaded", function () {
    const referenceInput = document.getElementById("referenceInput");
    const demandeBlock = document.getElementById("demandeBlock");
    const typeBlock = document.getElementById("typeBlock");
    const typeDevisSelect = document.getElementById("typeDevis");
    const addBlock = document.getElementById("addBlock");
    const lignesContainer = document.getElementById("lignesContainer");
    const btnAddLigne = document.getElementById("btnAddLigne");
    const template = document.getElementById("ligneDevisTemplate");
    const submitBlock = document.getElementById("submitBlock");

    let compteurLigne = 0;

    function gererVisibiliteBoutonEnregistrer() {
        const nbLignes = lignesContainer.querySelectorAll(".ligne-devis").length;

        if (nbLignes > 0) {
            submitBlock.classList.remove("hidden"); 
        } else {
            submitBlock.classList.add("hidden");    
        }
    }

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
                    document.getElementById("txtLieu").innerText = "Lieu : " + data.lieu;
                    document.getElementById("txtReference").innerText = "Réf : " + data.reference;
                    document.getElementById("txtClient").innerText = "Client : " + data.clientNom;
                    document.getElementById("txtCommune").innerText = "Commune : " + data.communeNom;
                    demandeBlock.classList.remove("hidden");
                    addBlock.classList.remove("hidden");

                    if (data.hasTypeDevis) {
                        typeDevisSelect.innerHTML = `<option value="${data.typeDevisId}">${data.typeDevisNom}</option>`;
                        typeBlock.classList.remove("hidden");

                        addBlock.classList.remove("hidden");
                    } else {
                        typeBlock.classList.add("hidden");
                        addBlock.classList.add("hidden");
                    }
                } else {
                    masquerTout();
                }
            })
            .catch(error => {
                console.error("Erreur lors du Fetch AJAX :", error);
            });
    });

    btnAddLigne.addEventListener("click", function () {
        const clone = template.content.cloneNode(true);
        const divLigne = clone.querySelector(".ligne-devis");
        divLigne.id = "ligne_" + compteurLigne;

        clone.querySelector(".input-designation").name = `lignes[${compteurLigne}].designation`;
        clone.querySelector(".input-description").name = `lignes[${compteurLigne}].description`;
        clone.querySelector(".input-qty").name = `lignes[${compteurLigne}].quantite`;
        clone.querySelector(".input-pu").name = `lignes[${compteurLigne}].prixUnitaire`;
        clone.querySelector(".input-total").name = `lignes[${compteurLigne}].montant`;

        const inputQty = clone.querySelector(".qty");
        const inputPu = clone.querySelector(".pu");
        const inputTotal = clone.querySelector(".total");

        function calculerMontant() {
            const qte = parseFloat(inputQty.value) || 0;
            const pu = parseFloat(inputPu.value) || 0;
            inputTotal.value = (qte * pu).toFixed(2);
        }
        inputQty.addEventListener("input", calculerMontant);
        inputPu.addEventListener("input", calculerMontant);

        clone.querySelector(".btn-remove").addEventListener("click", function () {
            divLigne.remove();
            gererVisibiliteBoutonEnregistrer();
        });

        lignesContainer.appendChild(clone);
        compteurLigne++;
        gererVisibiliteBoutonEnregistrer();
    });

    function masquerTout() {
        demandeBlock.classList.add("hidden");
        typeBlock.classList.add("hidden");
        addBlock.classList.add("hidden");
        submitBlock.classList.add("hidden");
        lignesContainer.innerHTML = "";
        compteurLigne = 0;
    }
});