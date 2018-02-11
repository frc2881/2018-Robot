#!/bin/bash

# Print something that does a reasonable job of identifying the current version of the code
echo $(date) - $(git name-rev --always HEAD)

# Print the first line of the last commit message
git log -1 --oneline --no-color
