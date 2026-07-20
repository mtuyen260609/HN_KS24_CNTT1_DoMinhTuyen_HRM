New-Item -ItemType Directory -Force -Path "C:\Users\Admin\OneDrive\HUMAN RESOURCE MANAGEMENT\src\main\resources\static\models"

$models = @(
  "ssd_mobilenetv1_model-weights_manifest.json",
  "ssd_mobilenetv1_model-shard1",
  "ssd_mobilenetv1_model-shard2",
  "face_landmark_68_model-weights_manifest.json",
  "face_landmark_68_model-shard1",
  "face_recognition_model-weights_manifest.json",
  "face_recognition_model-shard1",
  "face_recognition_model-shard2"
)

$baseUrl = "https://raw.githubusercontent.com/justadudewhohacks/face-api.js/master/weights/"

foreach ($model in $models) {
  Write-Host "Downloading $model ..."
  Invoke-WebRequest -Uri "$baseUrl$model" -OutFile "C:\Users\Admin\OneDrive\HUMAN RESOURCE MANAGEMENT\src\main\resources\static\models\$model"
}
Write-Host "All models downloaded successfully!"
