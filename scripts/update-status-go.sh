#!/usr/bin/env bash

if [[ ! -x "$(command -v nix-prefetch-url)" ]] && [[ -z "${IN_NIX_SHELL}" ]]; then
    echo "Remember to call 'make shell'!"
    exit 1
fi

set -ef

GIT_ROOT="$(cd "${BASH_SOURCE%/*}" && git rev-parse --show-toplevel)"
VERSION_FILE="${GIT_ROOT}/status-go-version.json"
SCRIPT_FILE="$(basename "$0")"

STATUS_GO_REPO="${STATUS_GO_REPO:=status-go}"
STATUS_GO_OWNER="${STATUS_GO_OWNER:=status-im}"
REPO_URL="https://github.com/${STATUS_GO_OWNER}/${STATUS_GO_REPO}"
STATUS_GO_VERSION=$1

HELP_MESSAGE=$(cat <<-END
This is a tool for upgrading status-go to a given version in:
${VERSION_FILE}
Which is then used by Nix derivations to build status-go for the app.
If the given name matches both a branch and a tag the tag is used.

Usage:
    ${SCRIPT_FILE} {version}

Examples:

    # Using branch name
    ${SCRIPT_FILE} feature-abc-xyz

    # Using tag name
    ${SCRIPT_FILE} v2.1.1

    # Using PR number
    ${SCRIPT_FILE} PR-2134
END
)

if [ "$1" = "-h" ] || [ "$1" = "--help" ]; then
    echo "${HELP_MESSAGE}"
    exit 1
fi

if [ $# -eq 0 ]; then
    echo "Need to provide a status-go version!"
    echo
    echo "${HELP_MESSAGE}"
    exit 1
fi

# If prefixed with # we assume argument is a PR number
if [[ "${STATUS_GO_VERSION}" = PR-* ]]; then
    STATUS_GO_VERSION="refs/pull/${STATUS_GO_VERSION#"PR-"}/head"
fi

# ls-remote finds only tags, branches, and pull requests, but can't find commits
STATUS_GO_MATCHING_REFS=$(git ls-remote ${REPO_URL} ${STATUS_GO_VERSION})

# It's possible that there's both a branch and a tag matching the given version
STATUS_GO_TAG_SHA1=$(echo "${STATUS_GO_MATCHING_REFS}" | grep 'refs/tags' | cut -f1)
STATUS_GO_BRANCH_SHA1=$(echo "${STATUS_GO_MATCHING_REFS}" | grep 'refs/heads' | cut -f1)

# Prefer tag over branch if both are found
if [[ -n "${STATUS_GO_TAG_SHA1}" ]]; then
    STATUS_GO_COMMIT_SHA1="${STATUS_GO_TAG_SHA1}"
else
    STATUS_GO_COMMIT_SHA1="${STATUS_GO_BRANCH_SHA1}"
fi

if [[ -z "${STATUS_GO_COMMIT_SHA1}" ]]; then
    echo "ERROR: Failed to find a SHA1 for rev '${STATUS_GO_VERSION}'."
    echo "NOTE: To set SHA1 you can just edit ${VERSION_FILE} by hand."
    exit 1
fi

STATUS_GO_SHA256=$(nix-prefetch-url --unpack ${REPO_URL}/archive/${STATUS_GO_VERSION}.zip)

cat << EOF > ${VERSION_FILE}
{
    "_comment": "DO NOT EDIT THIS FILE BY HAND. USE 'scripts/update-status-go.sh <tag>' instead",
    "owner": "${STATUS_GO_OWNER}",
    "repo": "${STATUS_GO_REPO}",
    "version": "${STATUS_GO_VERSION}",
    "commit-sha1": "${STATUS_GO_COMMIT_SHA1}",
    "src-sha256": "${STATUS_GO_SHA256}"
}
EOF

echo "SHA-1 for ${STATUS_GO_VERSION} is ${STATUS_GO_COMMIT_SHA1}.
SHA-256 for source archive is ${STATUS_GO_SHA256}
Owner is ${STATUS_GO_OWNER}"
