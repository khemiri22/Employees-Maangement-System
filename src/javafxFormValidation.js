function isNumber(inputString) {
      return !isNaN(inputString) && !isNaN(parseFloat(inputString));
}
function verifyStringLength(inputString) {
    return inputString.length >= 3 && inputString.length <= 15;
}
function verifyPhoneLength(inputString,maxNumber){
    return inputString.length == 8;
}

function verifyStringEmpty(inputString){
    return inputString.length ==0;
}
function isAllLetters(inputString){
    return /^[a-zA-Z]+$/.test(inputString);
}