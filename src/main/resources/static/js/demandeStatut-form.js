document.addEventListener("DOMContentLoaded", function () {

    const referenceInput = document.getElementById("referenceInput");

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
                document.getElementById("demandeId").value = data.id;

                document.getElementById("demandeLibelle").value = data.reference;

            })
            .catch(error => {
                console.error(error);

                document.getElementById("demandeId").value = "";
                document.getElementById("demandeLibelle").value = "";
            });

    });

});