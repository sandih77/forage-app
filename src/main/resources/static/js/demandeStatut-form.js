document.addEventListener("DOMContentLoaded", function () {

    const referenceInput = document.getElementById("referenceInput");
    const demandeIdInput = document.getElementById("demandeId");
    const demandeLibelleInput = document.getElementById("demandeLibelle");

    if (!referenceInput || !demandeIdInput || !demandeLibelleInput) {
        return;
    }

    referenceInput.addEventListener("input", function () {

        const refValue = this.value.trim();

        if (refValue.length === 0) {
            return;
        }

        const url = this.getAttribute("data-url");

        fetch(`${url}?reference=${encodeURIComponent(refValue)}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Erreur HTTP " + response.status);
                }
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

    });

});