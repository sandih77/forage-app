<?php
$reference = "";
$alertes = null;
$erreur = null;

if (isset($_GET['reference']) && !empty(trim($_GET['reference']))) {
    $reference = trim($_GET['reference']);

    $urlFindDemande = "http://localhost:8080/forage-app/devis/findDemande?reference=" . urlencode($reference);

    $demandeJson = @file_get_contents($urlFindDemande);

    if ($demandeJson !== FALSE) {
        $demandeData = json_decode($demandeJson, true);

        if ($demandeData && isset($demandeData['found']) && $demandeData['found'] == true) {
            $demandeId = $demandeData['id'];

            $urlAlertes = "http://localhost:8080/forage-app/demande/" . $demandeId . "/alertes";
            $alertesJson = @file_get_contents($urlAlertes);

            if ($alertesJson !== FALSE) {
                $alertes = json_decode($alertesJson, true);
            } else {
                $erreur = "Impossible de récupérer les alertes pour cette demande.";
            }
        } else {
            $erreur = "Aucune demande trouvée avec la référence : " . htmlspecialchars($reference);
        }
    } else {
        $erreur = "Le serveur de gestion (Java) est injoignable.";
    }
}
?>

<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vérification des Alertes — GestioPro</title>
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
            border-radius: 10px;
        }

        .form-control {
            background-color: #222736;
            border: 1.5px solid #2e3447;
            color: #f1f5f9;
        }

        .form-control:focus {
            background-color: #222736;
            border-color: #2dd4bf;
            color: #f1f5f9;
            box-shadow: 0 0 0 3px rgba(45, 212, 191, 0.2);
        }

        .btn-primary {
            background-color: #2dd4bf;
            border-color: #2dd4bf;
            color: #0f1117;
            font-weight: 500;
        }

        .btn-primary:hover {
            background-color: #5eead4;
            border-color: #5eead4;
            color: #0f1117;
        }

        .alerte-item {
            background-color: #222736;
            border: 1px solid #2e3447;
            border-radius: 8px;
            padding: 1.25rem;
            margin-bottom: 1rem;
            transition: transform 0.2s;
        }

        .alerte-item:hover {
            transform: translateY(-2px);
        }

        .txt-secondary {
            color: #94a3b8;
            font-size: 0.9rem;
        }
    </style>
</head>

<body>

    <div class="container py-5" style="max-width: 700px;">

        <h1 class="mb-2 text-center" style="font-family: serif;">GestioPro <span style="color: #2dd4bf;">•</span> Alertes</h1>
        <p class="text-center txt-secondary mb-5">Saisissez une référence pour analyser les retards et les alertes de traitement</p>

        <div class="card p-4 mb-4 shadow">
            <form action="" method="GET">
                <div class="mb-3">
                    <label for="reference" class="form-label text-uppercase fw-bold" style="font-size: 0.8rem; color: #94a3b8;">Référence de la demande</label>
                    <div class="input-group">
                        <span class="input-group-text bg-secondary border-0 text-white"><i class="bi bi-search"></i></span>
                        <input type="text" id="reference" name="reference" class="form-control"
                            placeholder="Ex: DEMANDE01" value="<?php echo htmlspecialchars($reference); ?>" required autocomplete="off">
                        <button type="submit" class="btn btn-primary px-4">Analyser</button>
                    </div>
                </div>
            </form>
        </div>

        <?php if ($erreur): ?>
            <div class="alert alert-danger d-flex align-items-center" role="alert">
                <i class="bi bi-exclamation-triangle-fill me-2"></i>
                <div><?php echo $erreur; ?></div>
            </div>
        <?php endif; ?>

        <?php if ($alertes !== null && !$erreur): ?>
            <div class="card p-4 shadow">
                <h3 class="h5 mb-4 border-bottom pb-2" style="color: #2dd4bf;">
                    <i class="bi bi-shield-exclamation me-2"></i>Résultat de l'analyse : <?php echo htmlspecialchars($reference); ?>
                </h3>

                <?php if (empty($alertes)): ?>
                    <div class="text-center py-4">
                        <i class="bi bi-check-circle-fill text-success fs-1 mb-2"></i>
                        <p class="mb-0 fw-bold text-success">Aucun retard détecté !</p>
                        <p class="txt-secondary small mb-0">Toutes les étapes respectent les délais impartis.</p>
                    </div>
                <?php else: ?>
                    <?php foreach ($alertes as $alt): ?>
                        <div class="alerte-item" style="border-left: 5px solid <?php echo htmlspecialchars($alt['couleur']); ?>;">
                            <div class="d-flex justify-content-between align-items-start">
                                <h5 class="h6 mb-2 fw-bold text-white">
                                    <i class="bi bi-arrow-right-circle-fill text-muted me-2"></i><?php echo htmlspecialchars($alt['transition']); ?>
                                </h5>
                                <span class="badge bg-danger rounded-pill px-2 py-1" style="font-size: 0.75rem;">
                                    Retard : <?php echo htmlspecialchars($alt['depassement']); ?>
                                </span>
                            </div>
                            <div class="row g-2 mt-1 txt-secondary">
                                <div class="col-6">
                                    <small>Temps max alloué :</small> <span class="text-white fw-medium"><?php echo htmlspecialchars($alt['dureeLimite']); ?></span>
                                </div>
                                <div class="col-6">
                                    <small>Temps réel consommé :</small> <span class="text-white fw-medium"><?php echo htmlspecialchars($alt['dureeReelle']); ?></span>
                                </div>
                            </div>
                        </div>
                    <?php endforeach; ?>
                <?php endif; ?>
            </div>
        <?php endif; ?>

    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>