
() => {
  if ("indeterminate" === this.element.indeterminate) return this.actions.addClass(this.option.displayNoneClassName), this.replacedElement.removeClass(this.option.displayNoneClassName), this.removeBulkCheck(), void this.bulkSelectRows.forEach((e => {
    const t = new DomNode(e);
    t.checked = !1, t.setAttribute("checked", !1);
  }));
  this.toggleDisplay(), this.bulkSelectRows.forEach((e => {
    e.checked = this.element.checked;
  }));
}