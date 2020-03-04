package rizky.f.sabtugas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_data.view.*

class FrdAdapter(var datas: ArrayList<Users>, var list_frd: Int) : RecyclerView.Adapter<FrdAdapter.ViewHolder>() {

    var onItemClick : ((Users) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = datas[position]
        holder.bind(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(room: Users) {
            itemView.txt_name.text = "nama : "+room.name
            itemView.txt_nrp.text = "NRP : "+room.nrp
            itemView.txt_info.text = "Jurusan : "+room.jurusan
            itemView.btn_edit.setOnClickListener {
                onItemClick?.invoke(room)
            }

        }
    }
    override fun onCreateViewHolder(views: ViewGroup, position: Int):ViewHolder {
        val layoutInflater = LayoutInflater.from(views.context).inflate(list_frd,views,false)
        return ViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}