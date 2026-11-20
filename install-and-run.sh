#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
BASE_DIR="${BASE_DIR:-$SCRIPT_DIR/../beakerx_kernel_base}"
VENV_DIR="${VENV_DIR:-/Users/edward.capriolo/dev/ai-code/transformers/.venv}"
MODE="${1:-lab}"

if [[ ! -d "$BASE_DIR" ]]; then
  echo "Base repository not found: $BASE_DIR" >&2
  exit 1
fi

if ! command -v java >/dev/null 2>&1; then
  echo "Java 25 is required on PATH." >&2
  exit 1
fi

JAVA_MAJOR="$(java -version 2>&1 | awk -F '[.\"]' '/version/ {print ($2 == 1 ? $3 : $2); exit}')"
if [[ "$JAVA_MAJOR" != "25" ]]; then
  echo "Java 25 is required; found Java $JAVA_MAJOR." >&2
  exit 1
fi

if [[ ! -x "$VENV_DIR/bin/python" ]]; then
  python3 -m venv "$VENV_DIR"
fi

source "$VENV_DIR/bin/activate"
python -m pip install --upgrade pip setuptools
python -m pip install jupyterlab jupyter-console

"$BASE_DIR/gradlew" clean publishToMavenLocal
"$SCRIPT_DIR/gradlew" clean build

python -m pip install --no-deps --no-build-isolation -e "$SCRIPT_DIR/groovy-dist"
beakerx_kernel_groovy install

case "$MODE" in
  lab)
    exec jupyter lab
    ;;
  console)
    exec jupyter console --kernel groovy
    ;;
  *)
    echo "Usage: $0 [lab|console]" >&2
    exit 2
    ;;
esac
