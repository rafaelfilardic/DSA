#include <stdio.h>
#include <stdbool.h>
#include <string.h>

bool palindrome(char *str, int i, int j) {
    if(i >= j) {
        return true;
    }
    else if(str[i] != str[j]) {
        return false;
    }
    else {
        return palindrome(str, i+1, j-1);
    }
}

int main() {
    char input[256];

    do {
        scanf(" %[^\n]", input);

        if(strcmp(input, "FIM") == 0) break;

        int size = strlen(input);
        
        if(palindrome(input, 0, size - 1)) {
            printf("SIM\n");
        }
        else printf("NAO\n");

    } while(true);

    return 0;
}
