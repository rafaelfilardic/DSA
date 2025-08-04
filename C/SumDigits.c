#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int sumDigits(int n){
    if(n == 0) return 0;

    return n % 10 + sumDigits(n / 10); 
}

int main() {
    char input[256];
    int number, sum;

    do {
        scanf(" %[^\n]", input);

        if(strcmp(input, "FIM") == 0) break;

        number = atoi(input);
        sum = sumDigits(number);

        printf("%d\n", sum);

    } while(1);

    return 0;
}
