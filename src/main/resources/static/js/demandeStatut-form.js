document.addEventListener("DOMContentLoaded", function () {

    const referenceInput = document.getElementById("referenceInput");
    const demandeIdInput = document.getElementById("demandeId");
    const demandeLibelleInput = document.getElementById("demandeLibelle");

    if (!referenceInput || !demandeIdInput || !demandeLibelleInput) return;

    // ✅ Pré-remplissage au chargement si on est en mode édition
    const initialRef = referenceInput.value.trim();
    if (initialRef.length > 0) {
        fetchDemande(referenceInput, initialRef);
    }

    referenceInput.addEventListener("input", function () {
        const refValue = this.value.trim();
        if (refValue.length === 0) return;
        fetchDemande(this, refValue);
    });

    function fetchDemande(inputEl, refValue) {
        const url = inputEl.getAttribute("data-url");

        fetch(`${url}?reference=${encodeURIComponent(refValue)}`)
            .then(response => {
                if (!response.ok) throw new Error("Erreur HTTP " + response.status);
                return response.json();
            })
            .then(data => {
                demandeIdInput.value = data.id;
                demandeLibelleInput.value = data.reference;
            })
            .catch(error => {
                console.error(error);
                demandeIdInput.value = "";
                demandeLibelleInput.value = "";
            });
    }

});