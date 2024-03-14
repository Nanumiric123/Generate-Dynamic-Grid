package com.example.generatedynamicgrid

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var MainLinearLayout:LinearLayout
    private lateinit var _context:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //declare context for functions outside of main method
        _context = this

        //initialize Main linear layout. This will be out parent view
        MainLinearLayout = findViewById(R.id.mainLinearHoriz)

        MainLinearLayout.addView(generateTable())

    }

    //Generate child view for MainLayout which is a table which returns table as View
    private fun generateTable(): View {
        //set table parameters first as we set the parameters for layout xml
        val tableParam: TableLayout.LayoutParams = TableLayout.LayoutParams(
            TableLayout.LayoutParams.MATCH_PARENT,
            TableLayout.LayoutParams.MATCH_PARENT
        )
        //set whatever attribute that you want in your table. This will effect the table in your end result in your app.
        with(tableParam){
            setMargins(10,10,10,10)
        }
        //set Parameters for rows
        val rowParams: TableRow.LayoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.MATCH_PARENT
        )
        //set whatever attribute that you want in your row. in this case i am adding the borders or the row with the thickness of 1F
        with(rowParams){
            weight = 1F
        }
        //create table with table Layout method passing context which we declared in main
        val table = TableLayout(_context)
        //generate table ID for if you want to reuse the table later
        table.id = View.generateViewId()
        //generate the table layout this is important
        table.layoutParams = tableParam
        //Add Header Row and pass the context this can be skipped when using for loop this table in main
        val headerRow = TableRow(_context)
        //set the row parameters this is important
        headerRow.layoutParams = TableRow.LayoutParams(rowParams)
        // you can generate view ID in case you want to call back the child row in main
        headerRow.id = View.generateViewId()

        //call the generate text view method we created below and add the view to your header row
        headerRow.addView(generateTVforRow("Material"))
        headerRow.addView(generateTVforRow("Quantity"))
        headerRow.addView(generateTVforRow("UOM"))
        headerRow.addView(generateTVforRow("Batch"))
        headerRow.addView(generateTVforRow("Storage Bin Number"))
        headerRow.addView(generateTVforRow("Button Column"))
        headerRow.addView(generateTVforRow("Input Column"))
        //add the header row to your table first
        table.addView(headerRow)

        // retrieve the data from whatever your source is from
        val data = generateData()

        //loop through this lists
        for (i in 0 until data.size){
            //call the method to generate rows passing the row parameters
            val rowView = generateRowsForTable(data[i],rowParams)
            //add row View to the table as child view
            table.addView(rowView)

        }

        return table
    }

    //create a method for generating rows for datatable here you can generate whatever row that you like
    private fun generateRowsForTable(currentRow:Item_Data,rowsParam:TableRow.LayoutParams):View{
        //Generate text view for each of the columns by calling the method we created below
        val materialTV = generateTVforRow(currentRow.material)
        val quantityTV = generateTVforRow(currentRow.quantity)
        val uomTV = generateTVforRow(currentRow.uom)
        val batchTV = generateTVforRow(currentRow.batch)
        val binTV = generateTVforRow(currentRow.storagebin)
        //Generate rows for your table later
        val row = TableRow(_context)
        //set layouts from the parameters in the method
        row.layoutParams = rowsParam
        //generate row ID in case you want to retrieve whatever value you want in textview child
        row.id = View.generateViewId()
        //generate text input for row in variable calling the method passing the button text
        val edForRow = generateEditTextForRow("edit text hint")
        //generate button value in variable calling the method passing the button text
        val btn:Button = generateButtonForRow("Button")
        //set the button click listener
        btn.setOnClickListener{
            Toast.makeText(_context, "Button for "+ currentRow.material, Toast.LENGTH_LONG).show()
        }
        //generate linear layout variable by calling the method and passing the row parameters and the button we created
        val buttonContainer = generateLinearContainer(rowsParam,btn)
        //generate linear layout variable for edittext
        val edContainer = generateLinearContainer(rowsParam,edForRow)
        //create rows with texts by add textview inside
        row.addView(materialTV)
        row.addView(quantityTV)
        row.addView(uomTV)
        row.addView(batchTV)
        row.addView(binTV)

        //add Linear Layout with button to current row
        row.addView(buttonContainer)
        //add Linear Layout with Edit Text to current row
        row.addView(edContainer)
        return row
    }

    // if you want to generate a button for each of the rows you must add this method to the row view
    private fun generateButtonForRow(btnText:String):Button{
        //declare a button as view
        val buttonRow = Button(_context)
        //set the button attribute or appearence
        with(buttonRow){
            //set text for button
            text = btnText
            //Set color
            setBackgroundColor(Color.GRAY)
            setTextColor(Color.WHITE)
            //set share and background color for button by using the drawable xml
            setBackgroundResource(R.drawable.button_draw)
            //set text size
            textSize = 16F
            //set the text alignment
            gravity = Gravity.CENTER
            //set the button size
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
        }


        return buttonRow
    }

    //generate text input for each of the rows
    private fun generateEditTextForRow(l_hint:String):EditText{
        val edRow = EditText(_context)
        with(edRow) {
            id = View.generateViewId()
            inputType = InputType.TYPE_CLASS_TEXT
            hint = l_hint
        }
        return edRow

    }

    //Create a function that returns the View of a textview for each of the rows
    private fun generateTVforRow(Displaytext:String):View{
        val generateTV = TextView(_context)
        //Set whatever view attributed here
        with(generateTV) {
            //generate an ID in case you want to retrieve the texts later in main method
            id = ViewCompat.generateViewId()
            //generate the text based on the parameters you've provided
            text = Displaytext
            //set the font size
            textSize = 16F
            //set the text color
            setTextColor(Color.BLACK)
            //set the alignment of text
            gravity = Gravity.CENTER
            //inner padding for your textview
            setPadding(10, 10, 10, 10)
            //set layout parameters for your textview so far everything is made to fit the screen
            layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT
            )
            //set background border for textview table the table visible
            //cell with border xml resource file you can copy in the res -> drawable in the project folder
            setBackgroundResource(R.drawable.cell_with_border)
        }
        return generateTV
    }


    //generate linearlayout for edit texts
    private fun generateLinearContainer(rowParams:TableRow.LayoutParams,edTx:EditText):LinearLayout{
        val containerLayout = LinearLayout(_context)
        with(containerLayout) {
            layoutParams = rowParams
            gravity = Gravity.CENTER
            setBackgroundResource(R.drawable.cell_with_border)
            setPadding(15, 15, 15, 15)
        }
        containerLayout.addView(edTx)
        return containerLayout
    }

    //generate linearlayout for buttons
    private fun generateLinearContainer(rowParams:TableRow.LayoutParams,btn:Button):LinearLayout{
        val containerLayout = LinearLayout(_context)
        with(containerLayout) {
            layoutParams = rowParams
            gravity = Gravity.CENTER
            setBackgroundResource(R.drawable.cell_with_border)
            setPadding(15, 15, 15, 15)
        }
        containerLayout.addView(btn)
        return containerLayout
    }



    //Create fake data using MutableList from dataclass - you can use whatever method you want
    //extract from any source that you wish to extract as long as it returns in a mutable list

    private fun generateData():MutableList<Item_Data>{
        var result:MutableList<Item_Data> = mutableListOf()
        result.add(Item_Data("B1ADMJ000003","22000","PC","VPPP050","6001238680"))
        result.add(Item_Data("B1ADMJ000003","18000","PC","VPPP051","6001238682"))
        result.add(Item_Data("B1BBCF000030","56000","PC","VPPP052","6001203632"))
        result.add(Item_Data("B3ACA0000267","10000","PC","VPPP053","6001112816"))
        result.add(Item_Data("B3ACA0000267","8000","PC","VPPP054","6001112817"))
        result.add(Item_Data("C0DBGYY07627","64000","PC","VPPP055","6001200402"))
        result.add(Item_Data("C0DBGYY07831","70000","PC","VPPP056","6001212880"))
        result.add(Item_Data("C0DBGYY08282","17000","PC","VPPP057","6001207965"))
        result.add(Item_Data("C0DBGYY08282","19000","PC","VPPP058","6001207966"))
        result.add(Item_Data("C1ZBZ0005328","10000","PC","VPPP059","6001200492"))
        result.add(Item_Data("C1ZBZ0006032","10000","PC","VPPP060","6001193811"))
        result.add(Item_Data("C1ZBZ0006032","12500","PC","VPPP061","6001193812"))
        result.add(Item_Data("C1ZBZ0006094","50000","PC","VPPP062","6001263693"))
        result.add(Item_Data("C3EBGY000067","40000","PC","VPPP063","6001227789"))
        result.add(Item_Data("ERJ2RHD1000X","370000","PC","VPPP064","6000927923"))
        result.add(Item_Data("F2A1H2R20069","8000","PC","VPPP065","6001252132"))
        result.add(Item_Data("F2A1H2R2A634","4000","PC","VPPP066","6001252480"))
        result.add(Item_Data("F2A1H4R70073","20000","PC","VPPP067","6001252134"))
        result.add(Item_Data("F2G1C470A482","24000","PC","VPPP068","6001200456"))
        return result
    }


    //create a data class for to create a structure for when you create your data list
    data class Item_Data(
        val material: String,
        val quantity: String,
        val uom: String,
        val storagebin: String,
        val batch: String
    )
}