function clickBulkCheckbox(bulkCheckbox, simpleCheckboxes) {
        bulkCheckbox.addEventListener('click', () => {
          if (!bulkCheckbox.checked) {
            bulkCheckbox.checked = false;
            bulkCheckbox.removeAttribute('indeterminate');
            simpleCheckboxes.forEach(el => {
                el.checked = false;
                el.nextElementSibling.value = false;
            });
          } else {
            bulkCheckbox.checked = true;
            simpleCheckboxes.forEach(el => {
                el.checked = true;
                el.nextElementSibling.value = true;
            });
          }
        });
    }

function clickSimpleCheckbox(bulkCheckbox, simpleCheckboxes) {
    simpleCheckboxes.forEach(el => {
        el.addEventListener('click', () => {

            if (!el.checked) {
                el.nextElementSibling.value = false;
            } else {
                el.nextElementSibling.value = true;
            }

            let checkedElements = document.querySelectorAll('[type="checkbox"]:not(#checkbox-bulk-members-select):checked');
            if(checkedElements.length === 0) {
                bulkCheckbox.checked = false;
                bulkCheckbox.removeAttribute('indeterminate');
            } else if(checkedElements.length === simpleCheckboxes.length) {
                bulkCheckbox.checked = true;
                bulkCheckbox.removeAttribute('indeterminate');
            } else {
                bulkCheckbox.checked = true;
                bulkCheckbox.setAttribute('indeterminate', true);
            }
        });
    });
}

const bulkCheckbox = document.querySelector('#checkbox-bulk-members-select');
const simpleCheckboxes = document.querySelectorAll('[type="checkbox"]:not(#checkbox-bulk-members-select)')

clickBulkCheckbox(bulkCheckbox, simpleCheckboxes);
clickSimpleCheckbox(bulkCheckbox, simpleCheckboxes);