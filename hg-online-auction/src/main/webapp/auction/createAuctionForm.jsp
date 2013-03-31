<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<form method="post" action="createAuction">
	<h2>HG AUCTION CREATE AUCTION</h2>
	<div class="row">
		<label for="itemNameInput" id="itemName">Item Name</label> 
		<input id="itemNameInput" name="itemName" type="text"/>
	</div>
	<div class="row">
		<label for="itemDescriptionInput" id="itemDescription">Item Description</label> 
		<textarea id="itemDesciptionInput" name="itemDescription" rows="3" cols="50"></textarea>
	</div>
	<div class="row">
		<label for="itemBasePriceInput" id="itemBasePrice">Item Base Price</label>
		<input id="itemBasePriceInput" name="itemBasePrice" type="text"/>
	</div>
	<div class="row">
		<input type="submit" value="Submit"/>
	</div>
</form>